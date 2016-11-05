package com.only5c.unsplash.controllers.home

import com.only5c.unsplash.controllers.BaseImageListController

class HomeImageListController() : BaseImageListController() {
    override fun loadImages() {
        api?.let {
            val call = it.getHome()
            call.enqueue(this)
        }
    }
}