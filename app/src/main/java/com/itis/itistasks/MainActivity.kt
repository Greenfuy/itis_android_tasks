package com.itis.itistasks

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.itis.itistasks.databinding.ActivityMainBinding
import com.itis.itistasks.ui.fragments.CoroutinesFragment
import com.itis.itistasks.ui.fragments.MainFragment
import com.itis.itistasks.ui.fragments.NotificationsFragment
import com.itis.itistasks.utils.AirplaneModeCallback
import com.itis.itistasks.utils.NotificationUtil
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private var airplaneModeCallbackPos: Int? = null
    private var workingCoroutinesCount = 0
    private var stopOnBg = false
    private var job: Job? = null


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationUtil.createNotificationChannels(this)
        checkAirplaneModeIsOn()
        requestNotificationPermission()

        findViewById<BottomNavigationView>(R.id.main_bottom_navigation)?.let {
            it.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.mainFragment -> {
                        replaceFragment(MainFragment())
                        true
                    }

                    R.id.notificationsFragment -> {
                        replaceFragment(NotificationsFragment())
                        true
                    }

                    R.id.coroutinesFragment -> {
                        replaceFragment(CoroutinesFragment())
                        true
                    }

                    else -> false
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_activity_container,
                MainFragment()
            )
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, fragment, fragment.javaClass.name)
            .commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        when (intent?.getIntExtra(ACTION, ACTION_EMPTY)) {
            ACTION_OPEN_APP -> {
                Snackbar
                    .make(
                        viewBinding.root,
                        getString(R.string.intent_action_open_app_message),
                        Snackbar.LENGTH_SHORT
                    )
                    .show()
            }

            ACTION_OPEN_SETTINGS -> {
                val bottomNavigationView: BottomNavigationView =
                    findViewById(R.id.main_bottom_navigation)
                bottomNavigationView.selectedItemId = R.id.notificationsFragment
            }
        }
    }

    private fun checkAirplaneModeIsOn() {
        val intentFilter = IntentFilter("android.intent.action.AIRPLANE_MODE")
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                AirplaneModeCallback.notify(context)
            }
        }
        registerReceiver(receiver, intentFilter)
        airplaneModeCallbackPos = AirplaneModeCallback.saveCallback(this) { fl ->
            viewBinding.layoutNoConnection.isVisible = fl
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                POST_NOTIFICATION_CODE
            )
        }
    }

    private fun showPermissionDeniedMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.notifications_has_been_disabled))
            .setMessage(getString(R.string.unable_notifications_in_the_application_settings))
            .setPositiveButton(R.string.settings) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                openAppSettings()
            }
            .setNegativeButton(getString(R.string.cancel)) {
                    dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }
    private fun openAppSettings() {
        val intent = Intent()
        intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = android.net.Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == POST_NOTIFICATION_CODE) {
            if (grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (
                    shouldShowRequestPermissionRationale(
                        android.Manifest.permission.POST_NOTIFICATIONS
                    )
                ) {
                    requestNotificationPermission()
                } else {
                    showPermissionDeniedMessage()
                }
            }
        }
    }

    fun runCoroutines(coroutinesCount: Int, async: Boolean, stopOnBg: Boolean) {
        workingCoroutinesCount = coroutinesCount
        this.stopOnBg = stopOnBg
        job = lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                repeat (coroutinesCount) {
                    when {
                        async -> {
                            launch { runCoroutine() }
                        }
                        else -> {
                            runCoroutine()
                        }
                    }
                }
            }
        }.apply {
            invokeOnCompletion { cause ->
                when (cause) {
                    null -> {
                        NotificationUtil.sendCoroutinesFinishedNotification(this@MainActivity)
                    }
                    is CancellationException -> {
                        Log.e(javaClass.name,
                            "Terminated $workingCoroutinesCount coroutine(s)")
                    }
                }
                job = null
            }
        }
    }

    private suspend fun runCoroutine() {
        delay(2500)
        workingCoroutinesCount--
    }

    override fun onStop() {
        if (stopOnBg) {
            job?.cancel(CancellationException(getString(R.string.app_is_closed)))
        }
        super.onStop()
    }

    override fun onDestroy() {
        airplaneModeCallbackPos?.let { AirplaneModeCallback.removeCallback(it) }
        super.onDestroy()
    }

    companion object {
        const val ACTION_EMPTY = 0
        const val ACTION_OPEN_APP = 1
        const val ACTION_OPEN_SETTINGS = 2
        const val ACTION = "action"
        private const val POST_NOTIFICATION_CODE = 10
    }
}