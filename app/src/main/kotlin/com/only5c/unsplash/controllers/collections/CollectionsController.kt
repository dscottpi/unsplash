package com.only5c.unsplash.controllers.collections

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.only5c.unsplash.R
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.adapters.CollectionsAdapter
import com.only5c.unsplash.api.UnsplashApi
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.Collection
import kotlinx.android.synthetic.main.collections_list.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CollectionsController : Fragment(), Callback<List<Collection>> {

    var collections: ArrayList<Collection> = ArrayList()
    var adapter: CollectionsAdapter? = null
    var controller: View? = null
    var api: UnsplashApi? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = context.inflate(R.layout.collections_list)

        val layoutManager = GridLayoutManager(context, 2)
        adapter = CollectionsAdapter(context)

        controller!!.collections.adapter = adapter
        controller!!.collections.layoutManager = layoutManager
        controller!!.swipe_refresh.onRefresh { loadCollections() }

        api = (activity as BaseActivity).api
        loadCollections()

        return controller
    }

    fun updateUi() {
        controller!!.loading.visibility = View.GONE
        controller!!.collections.visibility = View.VISIBLE
        controller!!.swipe_refresh.isRefreshing = false
    }

    private fun loadCollections() {
        api?.let {
            val call = it.getFeaturedCollections()
            call.enqueue(this)
        }
    }

    override fun onFailure(call: Call<List<Collection>>?, t: Throwable?) {
        context.toast("Failed to get Collections")
    }

    override fun onResponse(call: Call<List<Collection>>?, response: Response<List<Collection>>?) {
        updateUi()
        response?.body()?.let {
            collections.addAll(it.filter {
                val id = it.id
                !collections.any { it.id == id }
            })
            adapter!!.data = collections
        }
    }
}