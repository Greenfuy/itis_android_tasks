package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.itistasks.R
import com.itis.itistasks.data.CurrentUser
import com.itis.itistasks.data.db.entities.RateEntity
import com.itis.itistasks.data.db.repositories.FilmRepository
import com.itis.itistasks.data.db.repositories.RateRepository
import com.itis.itistasks.databinding.FragmentFilmPageBinding
import kotlinx.coroutines.launch

class FilmPageFragment : Fragment(R.layout.fragment_film_page) {

    private val viewBinding : FragmentFilmPageBinding
    by viewBinding(FragmentFilmPageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val filmName = arguments?.getString(FILM_NAME)
        if (filmName == null) {
            parentFragmentManager.popBackStack()
        } else {
            lifecycleScope.launch {
                val film = FilmRepository.getEntityByName(filmName)
                viewBinding.run {
                    tvFilmName.text = film.name
                    tvYear.text = film.year.toString()
                    tvDesc.text = film.description
                    setAvgRate(film.filmId, tvAvgRating)

                    val currentRate = RateRepository.get(film.filmId, CurrentUser.get())
                    if (currentRate != null) {
                        seekbarRate.progress = currentRate.rating
                        seekbarRate.isEnabled = false
                        tvSbValue.text = currentRate.rating.toString()
                        btnSubmit.isEnabled = false
                    }

                    seekbarRate.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                            tvSbValue.text = p1.toString()
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(p0: SeekBar?) {
                        }
                    })

                    btnSubmit.setOnClickListener {
                        lifecycleScope.launch {
                            RateRepository.add(
                                RateEntity(
                                    filmId = film.filmId,
                                    rating = seekbarRate.progress,
                                    userId = CurrentUser.get()
                                )
                            )

                            setAvgRate(film.filmId, tvAvgRating)
                        }
                        seekbarRate.isEnabled = false
                        btnSubmit.isEnabled = false
                    }
                }
            }
        }
    }

    private suspend fun setAvgRate(filmId: Int, tvAvgRating: TextView) {
        val rateAvg = RateRepository.getAvg(filmId)
        tvAvgRating.text = rateAvg.toString()
    }

    companion object {
        const val FILM_PAGE_FRAGMENT_TAG = "FILM_PAGE_FRAGMENT_TAG"
        private const val FILM_NAME = "filmName"

        fun newInstance(filmName: String) = FilmPageFragment().apply {
            arguments = Bundle().apply {
                putString(FILM_NAME, filmName)
            }
        }
    }

}