package com.itis.itistasks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itis.itistasks.ui.fragments.StartFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, StartFragment())
                .commit()
        }
    }
}