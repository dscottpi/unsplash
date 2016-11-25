package com.only5c.unsplash

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class ApertureApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}