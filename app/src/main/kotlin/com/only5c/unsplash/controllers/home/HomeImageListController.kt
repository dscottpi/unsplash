package com.only5c.unsplash.controllers.home

import com.only5c.unsplash.PHOTOS_CACHE_HOME
import com.only5c.unsplash.controllers.BaseImageListController
import com.only5c.unsplash.prefs.clearPhotosCache
import com.only5c.unsplash.prefs.getHomePhotos
import com.only5c.unsplash.prefs.updateHomePhotos

class HomeImageListController() : BaseImageListController() {

    override var caching: Boolean = true

    override fun loadImagesFromCache() {
        photos = getHomePhotos(context)
        if (photos.size > 0) {
            adapter?.data = photos
            updateUi()
        } else {
            loadImages()
        }
    }

    override fun cachePhotos() {
        updateHomePhotos(context, photos)
    }

    override fun clearCache() {
        clearPhotosCache(activity!!, PHOTOS_CACHE_HOME)
    }

    override fun loadImages() {
        api?.let {
            val call = it.getHome(pageNumber)
            call.enqueue(this)
        }
    }

}