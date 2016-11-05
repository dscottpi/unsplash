package com.only5c.unsplash.extensions

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.only5c.unsplash.SET_WALLPAPER
import com.only5c.unsplash.WRITE_EXTERNAL_STORAGE

fun AppCompatActivity.canWriteExternalStorage(code:() -> Unit) {
    if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE)
    } else {
        code.invoke()
    }
}

fun AppCompatActivity.canSetWallpaper(code:() -> Unit) {
    if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.SET_WALLPAPER)
            != PackageManager.PERMISSION_GRANTED) {

        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.SET_WALLPAPER),
                SET_WALLPAPER)
    } else {
        code.invoke()
    }
}