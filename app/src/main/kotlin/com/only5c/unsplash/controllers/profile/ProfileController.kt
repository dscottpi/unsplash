package com.only5c.unsplash.controllers.profile

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.only5c.unsplash.R
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.adapters.ProfileAdapter
import com.only5c.unsplash.controllers.BaseController
import com.only5c.unsplash.extensions.createTypeface
import com.only5c.unsplash.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.view.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileController(val userName: String, activity: BaseActivity, view: View) : BaseController(activity, view), Callback<User> {
    val courier = activity.createTypeface("courier.ttf")
    var user: User? = null
    var isBioCollapsed: Boolean = true
    var menu: Menu? = null

    override fun initView() {
        view.user_location.typeface = courier
        view.user_bio.typeface = courier
        view.user_name.translationY = 10f
        view.user_name.alpha = 0f
        view.user_bio.translationY = 10f
        view.user_bio.alpha = 0f
        view.user_location.translationY = 10f
        view.user_location.alpha = 0f
        getUser(userName)
    }

    override fun onMenuItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) {
            android.R.id.home -> activity.finish()
            R.id.launch -> activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(user?.portfolioUrl)))
        }
    }

    override fun onMenuCreated(menu: Menu) {
        this.menu = menu
    }

    private fun getUser(userName: String) {
        val call = activity.api!!.getUser(userName)
        call.enqueue(this)
    }

    private fun loadUser(user: User) {
        Picasso.with(activity).load(user.profileImage!!.large).placeholder(R.color.colorAccent).into(view.avatar)
        view.user_name.text = user.name
        view.user_location.text = user.location
        view.user_bio.text = user.bio
        view.user_name.animate().translationY(0f).alpha(1f).start()
        view.user_bio.animate().translationY(0f).alpha(1f).start()
        view.user_location.animate().translationY(0f).alpha(1f).start()
        view.user_pager.adapter = ProfileAdapter(view, user.username!!, activity, activity.supportFragmentManager)
        view.user_page_indicator.setViewPager(view.user_pager)
        view.user_bio.onClick {
            if (isBioCollapsed) {
                view.user_bio.maxLines = Integer.MAX_VALUE
                view.user_bio.ellipsize = null
            } else {
                view.user_bio.maxLines = 3
                view.user_bio.ellipsize = TextUtils.TruncateAt.END
            }
            isBioCollapsed = !isBioCollapsed
        }

        user.portfolioUrl?.let {
            menu?.findItem(R.id.launch)?.setVisible(true)
        }
    }

    override fun onFailure(call: Call<User>?, t: Throwable?) {
        activity.toast("Failed to Retrieve User")
    }

    override fun onResponse(call: Call<User>?, response: Response<User>?) {
        user = response?.body()
        loadUser(user!!)
    }
}