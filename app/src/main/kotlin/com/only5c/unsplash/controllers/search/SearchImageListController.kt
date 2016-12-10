package com.only5c.unsplash.controllers.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.only5c.unsplash.R
import com.only5c.unsplash.adapters.ImageListAdapter
import com.only5c.unsplash.api.UnsplashApi
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.Photo
import com.only5c.unsplash.models.PhotoSearch
import kotlinx.android.synthetic.main.image_list.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SearchImageListController() : Fragment(), Callback<PhotoSearch> {
    var api: UnsplashApi? = null
    var adapter: ImageListAdapter? = null
    var controller: View? = null
    var photos: ArrayList<Photo> = ArrayList()
    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pageNumber: Int = 1
    var search: String? = ""
        set(value) {
            field = value
            if (value != null && !value.equals("")) {
                photos = ArrayList()
                adapter?.data = photos
                controller?.images?.scrollToPosition(0)
                loadImages(value)
            }
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = context.inflate(R.layout.image_list)
        val itemAnimator = DefaultItemAnimator()
        itemAnimator.addDuration = 1000
        itemAnimator.removeDuration = 1000
        controller!!.images.itemAnimator = itemAnimator

        val layoutManager = LinearLayoutManager(context)
        controller!!.images.layoutManager = layoutManager

        adapter = ImageListAdapter(activity as AppCompatActivity)
        controller!!.images.adapter = adapter
        controller!!.images.setScrollViewCallbacks(activity as ObservableScrollViewCallbacks)

        controller!!.swipe_refresh.onRefresh { loadImages(search!!) }

        initPagination()

        return controller
    }

    fun updateUi() {
        controller!!.loading.visibility = View.GONE
        controller!!.images.visibility = View.VISIBLE
        controller!!.swipe_refresh.isRefreshing = false
    }

    fun loadImages(search: String) {
        val call = api?.getSearchPhotos(search, pageNumber)
        call?.enqueue(this)
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
                            pageNumber++
                            loadImages(search!!)
                        }
                    }
                }
            }
        })
    }

    override fun onResponse(call: Call<PhotoSearch>?, response: Response<PhotoSearch>?) {
        updateUi()
        response?.body()?.let {
            photos.addAll(it.photos!!.filter {
                val id = it.id
                !photos.any { it.id == id }
            })
            adapter!!.data = photos
        }
    }

    override fun onFailure(call: Call<PhotoSearch>?, t: Throwable?) {
        context.toast("Failed to get images")
    }
}