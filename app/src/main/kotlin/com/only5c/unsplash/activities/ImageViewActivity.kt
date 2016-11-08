package com.only5c.unsplash.activities

import android.content.Context
import android.content.Intent
import com.only5c.unsplash.PHOTO_ID_EXTRA
import com.only5c.unsplash.R
import com.only5c.unsplash.controllers.ImageViewController
import org.jetbrains.anko.backgroundDrawable

class ImageViewActivity : BaseActivity() {
    override var layoutResource: Int? = R.layout.activity_image_view
    override var toolbarPadding: Boolean? = false
    override var toolbarTitle: Int? = R.string.blank
    override var menuResource: Int? = R.menu.menu_default
    override var toolbarIcon: Int? = R.drawable.ic_back_white

    override fun initController() {
        val photoId = intent.getStringExtra(PHOTO_ID_EXTRA)
        controllers.add(ImageViewController(photoId, this, view!!))
        toolbar!!.backgroundDrawable = getDrawable(R.drawable.top_gradient)
        toolbar!!.setTitleTextColor(getColor(android.R.color.white))
        toolbar!!.setSubtitleTextColor(getColor(android.R.color.white))
    }

    companion object {
        fun launch(context: Context, photoId: String) {
            val intent = Intent(context, ImageViewActivity::class.java)
            intent.putExtra(PHOTO_ID_EXTRA, photoId)
            context.startActivity(intent)
        }
    }
}