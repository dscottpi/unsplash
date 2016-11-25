package com.only5c.unsplash.controllers.profile

import com.only5c.unsplash.controllers.BaseImageListController

class UserPhotosImageListController : BaseImageListController() {
    var userName: String? = null

    override fun loadImages() {
        val call = api?.getUserPhotos(userName!!, pageNumber)
        call?.enqueue(this)
    }
}