package com.itis.itistasks.ui.fragments

import android.app.Notification.VISIBILITY_PRIVATE
import android.app.Notification.VISIBILITY_PUBLIC
import android.app.Notification.VISIBILITY_SECRET
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.NotificationManager.IMPORTANCE_LOW
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.itistasks.R
import com.itis.itistasks.databinding.FragmentNotificationsBinding
import com.itis.itistasks.utils.NotificationSettings

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private val viewBinding: FragmentNotificationsBinding
    by viewBinding(FragmentNotificationsBinding::bind)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.run {
            recoverSettings()

            rgImportance.setOnCheckedChangeListener { rg, _ ->
                val importance = when(rg.checkedRadioButtonId) {
                    R.id.rb_medium -> IMPORTANCE_LOW
                    R.id.rb_high -> IMPORTANCE_DEFAULT
                    R.id.rb_urgent -> IMPORTANCE_HIGH
                    else -> 0
                }

                if (importance != 0) NotificationSettings.importance = importance
            }

            rgVisibility.setOnCheckedChangeListener { rg, _ ->
                val visibility = when(rg.checkedRadioButtonId) {
                    R.id.rb_public -> VISIBILITY_PUBLIC
                    R.id.rb_secret -> VISIBILITY_SECRET
                    R.id.rb_private -> VISIBILITY_PRIVATE
                    else -> -1
                }

                if (visibility != -1) NotificationSettings.visibility = visibility
            }

            cbHideText.setOnClickListener {
                NotificationSettings.hideText = cbHideText.isChecked
            }

            cbShowButtons.setOnClickListener {
                NotificationSettings.showButtons = cbShowButtons.isChecked
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun recoverSettings() {
        with(viewBinding) {
            val importance = NotificationSettings.importance
            val visibility = NotificationSettings.visibility

            rgImportance.check(
                when(importance) {
                    IMPORTANCE_LOW -> R.id.rb_medium
                    IMPORTANCE_DEFAULT -> R.id.rb_high
                    IMPORTANCE_HIGH -> R.id.rb_urgent
                    else -> -1
                }
            )

            rgVisibility.check(
                when(visibility) {
                    VISIBILITY_PUBLIC -> R.id.rb_public
                    VISIBILITY_SECRET -> R.id.rb_secret
                    VISIBILITY_PRIVATE -> R.id.rb_private
                    else -> -1
                }
            )

            cbHideText.isChecked = NotificationSettings.hideText
            cbShowButtons.isChecked = NotificationSettings.showButtons
        }
    }
}