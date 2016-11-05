package com.only5c.unsplash.helpers

import android.app.WallpaperManager
import android.content.Context
import com.only5c.unsplash.models.Photo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL

fun setWallpaper(context: Context, photo: Photo) {
    context.doAsync {
        val wpm = WallpaperManager.getInstance(context)
        val ins = URL(photo?.downloads?.downloadLocation).openStream()
        println(photo?.downloads?.downloadLocation)
        //wpm.setBitmap(view.image.imageBitmap)

        uiThread { context.toast("Wallpaper Set") }
    }
}