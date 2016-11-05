package com.only5c.unsplash.controllers.home

import android.view.MenuItem
import android.view.View
import com.only5c.unsplash.R
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.activities.SearchActivity
import com.only5c.unsplash.adapters.HomeAdapter
import com.only5c.unsplash.controllers.BaseController
import com.only5c.unsplash.helpers.getHomeDrawerContent
import kotlinx.android.synthetic.main.activity_home.view.*

class HomeController(activity: BaseActivity, view: View) : BaseController(activity, view) {
    override fun initView() {
        activity.initDrawerLayout(getHomeDrawerContent())
        view.pager.adapter = HomeAdapter(activity, activity.supportFragmentManager)
        view.page_indicator.setupWithViewPager(view.pager)
        activity.changeTabsFont()
    }

    override fun onMenuItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.search -> SearchActivity.launch(activity)
        }
    }
}