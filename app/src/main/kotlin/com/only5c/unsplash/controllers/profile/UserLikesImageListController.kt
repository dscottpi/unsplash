package com.only5c.unsplash.controllers.profile

import com.only5c.unsplash.USER_NAME_EXTRA
import com.only5c.unsplash.controllers.BaseImageListController

class UserLikesImageListController : BaseImageListController() {

    override fun loadImages() {
        val userName: String? = activity?.intent?.getStringExtra(USER_NAME_EXTRA)
        val call = api?.getUserLikes(userName!!, pageNumber)
        call?.enqueue(this)
    }
}