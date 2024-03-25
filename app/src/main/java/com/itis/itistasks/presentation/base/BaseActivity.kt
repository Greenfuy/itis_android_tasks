package com.itis.itistasks.presentation.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val fragmentContainerId: Int
}