package com.itis.itistasks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.itis.itistasks.data.CurrentUser
import com.itis.itistasks.di.ServiceLocator
import com.itis.itistasks.ui.fragments.AuthorizationFragment
import com.itis.itistasks.ui.fragments.FilmsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            if (CurrentUser.isEmpty()) {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.container, AuthorizationFragment(),
                        AuthorizationFragment.AUTHORIZATION_FRAGMENT_TAG
                    )
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.container, FilmsFragment(),
                        FilmsFragment.FILMS_FRAGMENT_TAG
                    )
                    .commit()
                removeUsersIfTwoMinutesPassed(System.currentTimeMillis())
            }
        }
    }

    private fun removeUsersIfTwoMinutesPassed(currentTimeMillis: Long) {
        val userDao = ServiceLocator.getDbInstance().getUserDao()

        val sevenDaysInMillis = 7 * 24 * 60 * 60 * 1000

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val usersToDelete =
                    userDao.getUsersForDeletion(currentTimeMillis - sevenDaysInMillis)

                usersToDelete?.forEach { userDao.delete(it) }
            }
        }
    }
}