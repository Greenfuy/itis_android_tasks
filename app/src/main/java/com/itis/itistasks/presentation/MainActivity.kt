package com.itis.itistasks.presentation

import android.os.Bundle
import com.itis.itistasks.R
import com.itis.itistasks.presentation.base.BaseActivity
import com.itis.itistasks.presentation.screens.main.MainFragment
import com.itis.itistasks.presentation.screens.main.MainFragment.Companion.MAIN_FRAGMENT_TAG

class MainActivity : BaseActivity() {

    override val fragmentContainerId: Int = R.id.main_activity_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    fragmentContainerId,
                    MainFragment(),
                    MAIN_FRAGMENT_TAG,
                )
                .commit()
        }
    }

}