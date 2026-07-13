package com.example.androidappdevelopmentmasterclassusingkotlin.PhotoBrowserApp

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.androidappdevelopmentmasterclassusingkotlin.R

internal const val FLICKR_QUERY = "FLICKR_QUERY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {
    private val TAG = "BaseActivity"
    internal fun activateToolbar(enableHome:Boolean){
        var toolbar = findViewById<View>(R.id.toolbar)as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}