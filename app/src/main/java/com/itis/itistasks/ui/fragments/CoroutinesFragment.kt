package com.itis.itistasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.itistasks.MainActivity
import com.itis.itistasks.R
import com.itis.itistasks.databinding.FragmentCoroutinesBinding
import com.itis.itistasks.utils.AirplaneModeCallback
import com.itis.itistasks.utils.CoroutineSettings

class CoroutinesFragment : Fragment(R.layout.fragment_coroutines) {

    private val viewBinding:
            FragmentCoroutinesBinding by viewBinding(FragmentCoroutinesBinding::bind)
    private var airplaneModeCallbackPos: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.run {
            airplaneModeCallbackPos = AirplaneModeCallback.saveCallback(requireContext()) { fl ->
                btnRun.isEnabled = !fl
            }
            seekbar.progress = CoroutineSettings.count
            tvSbProgress.text = CoroutineSettings.count.toString()
            cbAsync.isChecked = CoroutineSettings.async
            cbStopOnBg.isChecked = CoroutineSettings.stopOnBg

            seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    tvSbProgress.text = seekbar.progress.toString()
                    CoroutineSettings.count = seekbar.progress
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            })

            cbAsync.setOnClickListener { CoroutineSettings.async = cbAsync.isChecked }
            cbStopOnBg.setOnClickListener { CoroutineSettings.stopOnBg = cbStopOnBg.isChecked }

            btnRun.setOnClickListener {
                (requireActivity() as MainActivity).runCoroutines(
                    coroutinesCount = CoroutineSettings.count,
                    async = CoroutineSettings.async,
                    stopOnBg = CoroutineSettings.stopOnBg,
                )
            }
        }
    }

    override fun onDestroyView() {
        airplaneModeCallbackPos?.let { AirplaneModeCallback.removeCallback(it) }
        super.onDestroyView()
    }
}