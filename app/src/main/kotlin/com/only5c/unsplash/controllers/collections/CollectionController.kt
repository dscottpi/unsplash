package com.only5c.unsplash.controllers.collections

import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.view.View
import com.only5c.unsplash.COLLECTION_COVER_EXTRA
import com.only5c.unsplash.COLLECTION_ID_EXTRA
import com.only5c.unsplash.COLLECTION_SIZE_EXTRA
import com.only5c.unsplash.COLLECTION_TITLE_EXTRA
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.adapters.CollectionAdapter
import com.only5c.unsplash.controllers.BaseController
import com.only5c.unsplash.models.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_collection.view.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CollectionController(activity: BaseActivity, view: View) : BaseController(activity, view), Callback<List<Photo>> {

    var adapter: CollectionAdapter? = null
    var photos: ArrayList<Photo> = ArrayList()
    var pageNumber: Int = 1

    override fun initView() {
        val coverPhoto = activity.intent.getStringExtra(COLLECTION_COVER_EXTRA)
        Picasso.with(activity)
                .load(coverPhoto)
                .into(view.cover_photo)

        val layoutManager = GridLayoutManager(activity, 5)
        adapter = CollectionAdapter(activity)

        view.collection_photos.layoutManager = layoutManager
        view.collection_photos.adapter = adapter

        view.collection_title.text = activity.intent.getStringExtra(COLLECTION_TITLE_EXTRA)
        loadCollections()
    }

    private fun loadCollections() {
        val size = activity.intent.getIntExtra(COLLECTION_SIZE_EXTRA, 0)
        var numPages = size / 30
        val remainder = size % 30

        if (remainder > 0) { numPages++ }
        while (pageNumber <= numPages) {
            activity.api?.let {
                val id = activity.intent.getIntExtra(COLLECTION_ID_EXTRA, 0).toString()
                val call = it.getCollectionPhotos(id, pageNumber)
                call.enqueue(this)
            }

            pageNumber++
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) { android.R.id.home -> activity.finish() }
    }

    override fun onFailure(call: Call<List<Photo>>?, t: Throwable?) = activity.toast("Failed to load photos")

    override fun onResponse(call: Call<List<Photo>>?, response: Response<List<Photo>>?) {
        response?.body()?.let {
            photos.addAll(it.filter {
                val id = it.id
                !photos.any { it.id == id }
            })
            adapter!!.data = photos
        }
    }
}