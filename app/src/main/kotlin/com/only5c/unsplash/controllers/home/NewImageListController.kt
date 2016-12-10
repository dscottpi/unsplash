package com.only5c.unsplash.controllers.home

import com.only5c.unsplash.controllers.BaseImageListController
import com.only5c.unsplash.prefs.getNewPhotos
import com.only5c.unsplash.prefs.updateNewPhotos

class NewImageListController : BaseImageListController() {

    override var caching: Boolean = true

    override fun loadImagesFromCache() {
        photos = getNewPhotos(context)
        if (photos.size > 0) {
            adapter?.data = photos
            updateUi()
        } else {
            loadImages()
        }
    }

    override fun cachePhotos() {
        updateNewPhotos(context, photos)
    }

    override fun loadImages() {
        api?.let {
            val call = it.getNew(pageNumber)
            call.enqueue(this)
        }
    }
}