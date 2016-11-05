package com.only5c.unsplash.controllers.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.only5c.unsplash.R
import com.only5c.unsplash.adapters.ImageListAdapter
import com.only5c.unsplash.api.UnsplashApi
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.PhotoSearch
import kotlinx.android.synthetic.main.image_list.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchImageListController() : Fragment(), Callback<PhotoSearch> {
    var api: UnsplashApi? = null
    var adapter: ImageListAdapter? = null
    var controller: View? = null
    var search: String? = ""
        set(value) {
            field = value
            if (value != null && !value.equals("")) {
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

        return controller
    }

    fun updateUi() {
        controller!!.loading.visibility = View.GONE
        controller!!.images.visibility = View.VISIBLE
        controller!!.swipe_refresh.isRefreshing = false
    }

    fun loadImages(search: String) {
        val call = api?.getSearchPhotos(search)
        call?.enqueue(this)
    }

    override fun onResponse(call: Call<PhotoSearch>?, response: Response<PhotoSearch>?) {
        updateUi()
        adapter?.data = response?.body()?.photos!!
    }

    override fun onFailure(call: Call<PhotoSearch>?, t: Throwable?) {
        context.toast("Failed to get images")
    }
}