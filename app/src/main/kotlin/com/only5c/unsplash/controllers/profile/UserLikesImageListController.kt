package com.only5c.unsplash.controllers.profile

import com.only5c.unsplash.controllers.BaseImageListController

class UserLikesImageListController : BaseImageListController() {
    var userName: String? = null

    override fun loadImages() {
        val call = api?.getUserLikes(userName!!, pageNumber)
        call?.enqueue(this)
    }
}