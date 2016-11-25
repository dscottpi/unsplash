package com.only5c.unsplash.controllers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
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
import java.util.*

abstract class BaseImageListController() : Fragment(), Callback<List<Photo>> {
    var api: UnsplashApi? = null
    var adapter: ImageListAdapter? = null
    var controller: View? = null
    var photos: ArrayList<Photo> = ArrayList()
    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pageNumber: Int = 1


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = context.inflate(R.layout.image_list)

        api = (activity as BaseActivity).api

        val itemAnimator = DefaultItemAnimator()
        itemAnimator.addDuration = 1000
        itemAnimator.removeDuration = 1000
        controller!!.images.itemAnimator = itemAnimator

        val layoutManager = LinearLayoutManager(activity)
        controller!!.images.layoutManager = layoutManager

        adapter = ImageListAdapter(activity as AppCompatActivity)
        controller!!.images.adapter = adapter
        controller!!.images.setItemViewCacheSize(20)
        controller!!.images.setDrawingCacheEnabled(true);
        controller!!.images.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        controller!!.swipe_refresh.onRefresh { loadImages() }

        if (adapter?.data!!.size > 1) {
            updateUi()
        }

        loadImages()
        initPagination()
        return controller
    }

    fun initPagination() {
        controller!!.images.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = controller!!.images.layoutManager.childCount
                    totalItemCount = controller!!.images.layoutManager.itemCount
                    pastVisiblesItems = (controller!!.images.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false
                            Log.v("...", "Last Item Wow !")
                            pageNumber++
                            loadImages()
                        }
                    }
                }
            }
        })
    }

    fun updateUi() {
        controller!!.loading.visibility = View.GONE
        controller!!.images.visibility = View.VISIBLE
        controller!!.swipe_refresh.isRefreshing = false
    }

    open fun loadImages() {
    }

    override fun onResponse(call: Call<List<Photo>>?, response: Response<List<Photo>>?) {
        loading = true
        updateUi()
        response?.body()?.let {
            photos.addAll(it.filter {
                val id = it.id
                !photos.any { it.id == id }
            })
            adapter!!.data = photos
        }
    }

    override fun onFailure(call: Call<List<Photo>>?, t: Throwable?) {
        context.toast("Failed to get images")
    }
}