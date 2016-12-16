package com.only5c.unsplash.controllers

import android.app.WallpaperManager
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.extensions.canSetWallpaper
import com.only5c.unsplash.extensions.canWriteExternalStorage
import com.only5c.unsplash.helpers.downloadPhoto
import com.only5c.unsplash.helpers.logDownloadButtonClicked
import com.only5c.unsplash.helpers.logWallpaperClicked
import com.only5c.unsplash.models.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_view.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageViewController(val photoId: String, activity: BaseActivity, view: View) : BaseController(activity, view), Callback<Photo> {
    var photo: Photo? = null
    var barsShowing: Boolean = true

    override fun initView() {
        val call = activity.api?.getPhoto(photoId)
        call?.enqueue(this)

        initBottomButtons()
    }

    private fun initBottomButtons() {
        view.download.onClick {
            logDownloadButtonClicked()
            photo?.let {
                activity.canWriteExternalStorage { downloadPhoto(activity, it) }
            }
        }

        view.wallpaper.onClick {
            logWallpaperClicked()
            activity.canSetWallpaper {
                doAsync {
                    val wpm = WallpaperManager.getInstance(activity)
                    view.image.buildDrawingCache()

                    val bitmap = view.image.drawingCache
                    wpm.setBitmap(bitmap)

                    uiThread { activity.toast("Wallpaper Set") }
                }
            }
        }
    }

    private fun toggleBars() {
        if (barsShowing) {
            view.bottom_bar.animate().translationY(view.bottom_bar.height.toFloat()).start()
            view.toolbar.animate().translationY(-view.toolbar.height.toFloat()).start()
        } else {
            view.bottom_bar.animate().translationY(0f).start()
            view.toolbar.animate().translationY(0f).start()
        }

        barsShowing = !barsShowing
    }

    private fun hideBarsWithDelay() {
        doAsync {
            Thread.sleep(4000)
            onComplete { toggleBars() }
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
                hideBarsWithDelay()
            }
        }

        Picasso.with(activity).load(photo?.image?.regular).into(view.image)
        view.toolbar.title = photo?.user?.name
        view.toolbar.subtitle = "${photo?.likes} likes"
        view.wallpaper.isClickable = true
        view.download.isClickable = true
        view.image.onClick { toggleBars() }
    }

    override fun onFailure(call: Call<Photo>?, t: Throwable?) {
        activity.toast("Failed to load photo")
    }
}