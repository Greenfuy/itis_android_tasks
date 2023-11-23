package com.itis.itistasks.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.itistasks.R
import com.itis.itistasks.databinding.FragmentMainBinding
import com.itis.itistasks.utils.AirplaneModeCallback
import com.itis.itistasks.utils.NotificationUtil
import com.itis.itistasks.utils.TextNotificationSettings

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewBinding: FragmentMainBinding
    by viewBinding(FragmentMainBinding::bind)
    private var airplaneModeCallbackPos: Int? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.run {
            airplaneModeCallbackPos = AirplaneModeCallback.saveCallback(requireContext()) { fl ->
                btnShow.isEnabled = !fl
            }

            etTitle.setText(TextNotificationSettings.title)
            etMessage.setText(TextNotificationSettings.message)

            btnShow.setOnClickListener {
                TextNotificationSettings.title = etTitle.text.toString()
                TextNotificationSettings.message = etMessage.text.toString()

                NotificationUtil.sendNotification(requireContext())
            }
        }
    }

    override fun onDestroyView() {
        airplaneModeCallbackPos?.let { AirplaneModeCallback.removeCallback(it) }
        super.onDestroyView()
    }
}