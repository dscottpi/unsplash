package com.only5c.unsplash.controllers

import android.app.WallpaperManager
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.extensions.canSetWallpaper
import com.only5c.unsplash.extensions.canWriteExternalStorage
import com.only5c.unsplash.helpers.downloadPhoto
import com.only5c.unsplash.models.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_view.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageViewController(val photoId: String, activity: BaseActivity, view: View) : BaseController(activity, view), Callback<Photo> {
    var photo: Photo? = null

    override fun initView() {
        val call = activity.api?.getPhoto(photoId)
        call?.enqueue(this)

        view.download.onClick {
            photo?.let {
                activity.canWriteExternalStorage { downloadPhoto(activity, it) }
            }
        }

        view.wallpaper.onClick {
            activity.canSetWallpaper {
                doAsync {
                    val wpm = WallpaperManager.getInstance(activity)
                    println(photo?.downloads?.downloadLocation)
                    println(photo?.downloads?.downloadUrl)

                    view.image.buildDrawingCache()
                    val bitmap = view.image.drawingCache
                    wpm.setBitmap(bitmap)

                    uiThread { activity.toast("Wallpaper Set") }
                }
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) { android.R.id.home -> activity.finish() }
    }

    override fun onResponse(call: Call<Photo>?, response: Response<Photo>?) {
        photo = response?.body() ?: return

        photo?.let {
            if (photo?.height!! > photo?.width!!) {
                val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
                view.image.layoutParams = params
            }
        }

        Picasso.with(activity).load(photo?.image?.regular).into(view.image)
        view.toolbar.title = photo?.user?.name
        view.toolbar.subtitle = "${photo?.likes} likes"
    }

    override fun onFailure(call: Call<Photo>?, t: Throwable?) {
        activity.toast("Failed to load photo")
    }
}