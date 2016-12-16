package com.only5c.unsplash.controllers.profile

import com.only5c.unsplash.USER_NAME_EXTRA
import com.only5c.unsplash.controllers.BaseImageListController

class UserPhotosImageListController : BaseImageListController() {

    override fun loadImages() {
        val userName = activity?.intent?.getStringExtra(USER_NAME_EXTRA)
        api?.let {
            val call = it.getUserPhotos(userName!!, pageNumber)
            call.enqueue(this)
        }
    }
}