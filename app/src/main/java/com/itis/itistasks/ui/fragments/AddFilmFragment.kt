package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.itistasks.R
import com.itis.itistasks.data.db.repositories.FilmRepository
import com.itis.itistasks.data.model.FilmModel
import com.itis.itistasks.databinding.FragmentAddFilmBinding
import kotlinx.coroutines.launch

class AddFilmFragment : Fragment(R.layout.fragment_add_film) {

    private val viewBinding : FragmentAddFilmBinding
    by viewBinding(FragmentAddFilmBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.run {
            btnSubmit.setOnClickListener {
                val filmName = etFilmName.text.toString()
                val year = etYear.text.toString()
                val description = etDesc.text.toString()

                if (filmName.isNotEmpty() && year.isNotEmpty() && description.isNotEmpty()) {
                    lifecycleScope.launch {
                        if (FilmRepository.checkNameExists(filmName)) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.film_with_this_name_already_exists),
                                Toast.LENGTH_SHORT
                            ).show()
                            etFilmName.text?.clear()
                            etYear.text?.clear()
                            etDesc.text?.clear()
                        } else {
                            val film = FilmModel(
                                name = filmName,
                                year = year.toInt(),
                                description = description
                            )

                            if (FilmRepository.add(film)) {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.film_added),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            parentFragmentManager.beginTransaction()
                                .replace(
                                    R.id.container,
                                    FilmPageFragment.newInstance(film.name),
                                    FilmPageFragment.FILM_PAGE_FRAGMENT_TAG
                                )
                                .addToBackStack(null)
                                .commit()
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fill_all_fields),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    companion object {
        const val ADD_FILM_FRAGMENT_TAG = "ADD_FILM_FRAGMENT_TAG"
    }
}