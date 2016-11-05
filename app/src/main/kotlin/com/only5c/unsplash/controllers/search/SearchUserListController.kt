package com.only5c.unsplash.controllers.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.only5c.unsplash.R
import com.only5c.unsplash.adapters.UserListAdapter
import com.only5c.unsplash.api.UnsplashApi
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.UserSearch
import kotlinx.android.synthetic.main.user_list.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserListController : Fragment(), Callback<UserSearch> {
    var api: UnsplashApi? = null
    var adapter: UserListAdapter? = null
    var controller: View? = null
    var search: String? = ""
        set(value) {
            field = value
            if (value != null && !value.equals("")) {
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

        return controller
    }

    fun updateUi() {
        controller!!.loading.visibility = View.GONE
        controller!!.users.visibility = View.VISIBLE
        controller!!.swipe_refresh.isRefreshing = false
    }

    fun loadUsers(search: String) {
        val call = api?.getSearchUsers(search)
        call?.enqueue(this)
    }

    override fun onResponse(call: Call<UserSearch>?, response: Response<UserSearch>?) {
        updateUi()
        adapter?.data = response?.body()?.users!!
    }

    override fun onFailure(call: Call<UserSearch>?, t: Throwable?) {
        context.toast("Failed to get users")
    }
}