package com.only5c.unsplash.controllers.home

import com.only5c.unsplash.controllers.BaseImageListController

class NewImageListController : BaseImageListController() {
    override fun loadImages() {
        api?.let {
            val call = it.getNew()
            call.enqueue(this)
        }
    }
}