package com.itis.itistasks.presentation.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.itis.itistasks.R
import com.itis.itistasks.data.exceptions.TooManyRequestsException
import com.itis.itistasks.data.runCatching
import com.itis.itistasks.databinding.FragmentMainBinding
import com.itis.itistasks.di.ServiceLocator
import com.itis.itistasks.presentation.base.BaseFragment
import com.itis.itistasks.presentation.model.WeatherUiModel
import com.itis.itistasks.utils.insertIconIdInUrl
import com.itis.itistasks.utils.temperatureToPrettyFormat
import kotlinx.coroutines.launch

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewBinding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnSubmit.setOnClickListener {
            setWeatherLoading(true)
            lifecycleScope.launch {
                runCatching(ServiceLocator.exceptionHandlerDelegate) {
                    ServiceLocator.getWeatherUseCase.invoke(viewBinding.etCity.text.toString())
                }.onSuccess {
                    setWeatherData(it)
                    setWeatherLoading(false)
                }.onFailure {
                    when (it) {
                        is TooManyRequestsException -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {
                            AlertDialog.Builder(requireContext())
                                .setMessage(it.message)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setWeatherData(weather: WeatherUiModel) {
        with(viewBinding) {
            val iconUrl = insertIconIdInUrl(weather.icon)
            println("TEST TAG - iconUrl: $iconUrl")
            ivWeather.load(iconUrl)

            val temperature = temperatureToPrettyFormat(
                weather.mainData.temperature.toString()
            )
            // Idk how to extract this strings please don't punish me :(
            println("TEST TAG - Temp: $temperature")
            tvWeather.text = temperature
        }

    }

    private fun setWeatherLoading(isLoading: Boolean) {
        with(viewBinding) {
            pbProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvWeather.visibility = if (isLoading) View.GONE else View.VISIBLE
            ivWeather.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MainFragment"
    }
}