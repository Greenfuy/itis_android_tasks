package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.itistasks.R
import com.itis.itistasks.adapter.FilmAdapter
import com.itis.itistasks.data.CurrentUser
import com.itis.itistasks.data.db.repositories.FilmRepository
import com.itis.itistasks.data.db.repositories.UserRepository
import com.itis.itistasks.data.model.FilmModel
import com.itis.itistasks.data.model.RvFilmModel
import com.itis.itistasks.databinding.FragmentFilmsBinding
import com.itis.itistasks.utils.SpinnerTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FilmsFragment : Fragment(R.layout.fragment_films) {

    private val viewBinding : FragmentFilmsBinding
    by viewBinding(FragmentFilmsBinding::bind)

    private var filmAdapter: FilmAdapter? = null
    private var favoritesAdapter: FilmAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewBinding.run {
            btnAddNewFilm.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .add(
                        R.id.container, AddFilmFragment(), AddFilmFragment.ADD_FILM_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }

            btnProfile.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .add(
                        R.id.container, ProfileFragment(), ProfileFragment.PROFILE_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }

            spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    lifecycleScope.launch {
                        async(Dispatchers.IO) {
                            val filmsList = when (p2) {
                                SpinnerTypes.RATING_ASC -> FilmRepository.getAllByRatingAsc()
                                SpinnerTypes.RATING_DESC -> FilmRepository.getAllByRatingDesc()
                                SpinnerTypes.YEAR_ASC -> FilmRepository.getAllByYearAsc()
                                else -> FilmRepository.getAllByYearDesc()
                            }
                            showAllFilms(filmsList)
                        }.await()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }
    }

    private fun initRecyclerView() {
        filmAdapter = FilmAdapter(
            onItemClicked = ::onFilmClicked,
            onBtnClicked = ::onFavoriteCbClicked,
            onLongClicked = ::onLongClicked
        )
        favoritesAdapter = FilmAdapter(
            onItemClicked = ::onFilmClicked,
            onBtnClicked = ::onFavoriteCbInFavoritesClicked,
            onLongClicked = ::onLongClicked
        )

        with(viewBinding) {
            rvFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
            rvFavorites.adapter = favoritesAdapter

            rvFilms.layoutManager = GridLayoutManager(requireContext(), 2)
            rvFilms.adapter = filmAdapter

            lifecycleScope.launch {
                async(Dispatchers.IO) {
                    showAllFavorites()
                    showAllFilms(FilmRepository.getAllByYearDesc())
                }.await()
            }
        }
    }

    private fun onFilmClicked(rvFilmModel: RvFilmModel) {
        parentFragmentManager.beginTransaction()
            .add(
                R.id.container,
                FilmPageFragment.newInstance(rvFilmModel.name),
                FilmPageFragment.FILM_PAGE_FRAGMENT_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun onFavoriteCbClicked(position: Int, rvFilmModel: RvFilmModel) {
        lifecycleScope.launch {
            if (rvFilmModel.isFavorite) {
                UserRepository.addFavorite(CurrentUser.get(), rvFilmModel.name)
                rvFilmModel.isFavorite = true
            } else {
                UserRepository.deleteFavorite(CurrentUser.get(), rvFilmModel.name)
                rvFilmModel.isFavorite = false
            }
            activity?.runOnUiThread {
                filmAdapter?.updateItem(position, rvFilmModel)
            }
            async(Dispatchers.IO) { showAllFavorites() }.await()
        }
    }

    private fun onFavoriteCbInFavoritesClicked(position: Int, rvFilmModel: RvFilmModel) {
        lifecycleScope.launch {
            UserRepository.deleteFavorite(CurrentUser.get(), rvFilmModel.name)
            activity?.runOnUiThread {
                rvFilmModel.isFavorite = false
                filmAdapter?.updateItem(position, rvFilmModel)
            }
            async(Dispatchers.IO) { showAllFavorites() }.await()
        }
    }

    private fun onLongClicked(rvFilmModel: RvFilmModel) : Boolean {
        lifecycleScope.launch {
            FilmRepository.delete(rvFilmModel.name)
            async(Dispatchers.IO) {
                if (UserRepository.isFavorite(CurrentUser.get(), rvFilmModel.name)) {
                UserRepository.deleteFavorite(CurrentUser.get(), rvFilmModel.name)
            } }.await()
            showAllFavorites()
            showAllFilms(FilmRepository.getAllByYearDesc())
        }
        return true
    }

    private suspend fun showAllFavorites() {
        val films: List<FilmModel> = UserRepository.getAllFavorites(CurrentUser.get())
        val newList = mutableListOf<RvFilmModel>()
        films.map {
            val rvFilmModel = it.toRvModel()
            rvFilmModel.isFavorite = true
            newList.add(rvFilmModel)
        }
        activity?.runOnUiThread {
            favoritesAdapter?.setItems(newList)
        }
    }

    private suspend fun showAllFilms(films: List<FilmModel>?) {
        val newList = mutableListOf<RvFilmModel>()
        if (films.isNullOrEmpty()) {
            activity?.runOnUiThread {
                viewBinding.tvNoFilms.visibility = View.VISIBLE
                viewBinding.rvFilms.visibility = View.GONE
                viewBinding.rvFavorites.visibility = View.GONE
                viewBinding.spinnerFilter.visibility = View.GONE
            }
        } else {
            films.map {
                val rvFilmModel = it.toRvModel()
                rvFilmModel.isFavorite = UserRepository.isFavorite(
                    CurrentUser.get(),
                    rvFilmModel.name
                )
                newList.add(rvFilmModel)
            }
            activity?.runOnUiThread {
                viewBinding.spinnerFilter.visibility = View.VISIBLE
                viewBinding.tvNoFilms.visibility = View.GONE
                viewBinding.rvFilms.visibility = View.VISIBLE
                viewBinding.rvFavorites.visibility = View.VISIBLE
                filmAdapter?.setItems(newList)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        filmAdapter = null
        favoritesAdapter = null
    }


    companion object {
        const val FILMS_FRAGMENT_TAG = "FILMS_FRAGMENT_TAG"
    }
}
