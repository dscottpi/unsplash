package com.only5c.unsplash.controllers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.only5c.unsplash.R
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.adapters.ImageListAdapter
import com.only5c.unsplash.api.UnsplashApi
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.Photo
import kotlinx.android.synthetic.main.image_list.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseImageListController() : Fragment(), Callback<List<Photo>> {
    var api: UnsplashApi? = null
    var adapter: ImageListAdapter? = null
    var controller: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = context.inflate(R.layout.image_list)

        val base = activity as BaseActivity
        api = base.api

        val itemAnimator = DefaultItemAnimator()
        itemAnimator.addDuration = 1000
        itemAnimator.removeDuration = 1000
        controller!!.images.itemAnimator = itemAnimator

        val layoutManager = LinearLayoutManager(activity)
        controller!!.images.layoutManager = layoutManager

        adapter = ImageListAdapter(activity as AppCompatActivity)
        controller!!.images.adapter = adapter

        controller!!.swipe_refresh.onRefresh { loadImages() }

        if (adapter?.data!!.size > 1) {
            context.toast(adapter!!.data.size.toString())
            updateUi()
        }

        loadImages()
        return controller
    }

    fun updateUi() {
        controller!!.loading.visibility = View.GONE
        controller!!.images.visibility = View.VISIBLE
        controller!!.swipe_refresh.isRefreshing = false
    }

    open fun loadImages() {}

    override fun onResponse(call: Call<List<Photo>>?, response: Response<List<Photo>>?) {
        updateUi()
        response?.body()?.let { adapter!!.data = it }
    }

    override fun onFailure(call: Call<List<Photo>>?, t: Throwable?) {
        context.toast("Failed to get images")
    }
}