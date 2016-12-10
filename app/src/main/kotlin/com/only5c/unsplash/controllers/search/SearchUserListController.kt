package com.only5c.unsplash.controllers.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.only5c.unsplash.R
import com.only5c.unsplash.adapters.UserListAdapter
import com.only5c.unsplash.api.UnsplashApi
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.User
import com.only5c.unsplash.models.UserSearch
import kotlinx.android.synthetic.main.user_list.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SearchUserListController : Fragment(), Callback<UserSearch> {
    var api: UnsplashApi? = null
    var adapter: UserListAdapter? = null
    var controller: View? = null
    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pageNumber: Int = 1
    var users: ArrayList<User> = ArrayList()
    var search: String? = ""
        set(value) {
            field = value
            if (value != null && !value.equals("")) {
                users = ArrayList()
                adapter?.data = users
                controller!!.users.smoothScrollToPosition(0)
                loadUsers(value)
            }
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = context.inflate(R.layout.user_list)

        val itemAnimator = DefaultItemAnimator()
        itemAnimator.addDuration = 1000
        itemAnimator.removeDuration = 1000
        controller!!.users.itemAnimator = itemAnimator

        val layoutManager = LinearLayoutManager(context)
        controller!!.users.layoutManager = layoutManager

        adapter = UserListAdapter(context)
        controller!!.users.adapter = adapter

        controller!!.swipe_refresh.onRefresh { loadUsers(search!!) }
        initPagination()

        return controller
    }

    fun updateUi() {
        controller!!.loading.visibility = View.GONE
        controller!!.users.visibility = View.VISIBLE
        controller!!.swipe_refresh.isRefreshing = false
    }

    fun loadUsers(search: String) {
        val call = api?.getSearchUsers(search, pageNumber)
        call?.enqueue(this)
    }

    fun initPagination() {
        controller!!.users.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = controller!!.users.layoutManager.childCount
                    totalItemCount = controller!!.users.layoutManager.itemCount
                    pastVisiblesItems = (controller!!.users.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false
                            pageNumber++
                            loadUsers(search!!)
                        }
                    }
                }
            }
        })
    }

    override fun onResponse(call: Call<UserSearch>?, response: Response<UserSearch>?) {
        updateUi()
        response?.body()?.let {
            users.addAll(it.users!!.filter {
                val id = it.id
                !users.any { it.id == id }
            })

            adapter!!.data = users
        }
    }

    override fun onFailure(call: Call<UserSearch>?, t: Throwable?) {
        context.toast("Failed to get users")
    }
}