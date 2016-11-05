package com.only5c.unsplash.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.controllers.home.HomeImageListController
import com.only5c.unsplash.controllers.search.SearchImageListController
import com.only5c.unsplash.controllers.search.SearchUserListController

class SearchAdapter(val activity: BaseActivity, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val TITLES = arrayOf("photos", "users")

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val controller = SearchImageListController()
                controller.api = activity.api
                return controller
            }

            1 -> {
                val controller = SearchUserListController()
                controller.api = activity.api
                return controller
            }
        }

        val controller = HomeImageListController()
        controller.api = activity.api
        return controller
    }

    override fun getPageTitle(position: Int): CharSequence = TITLES[position]

    override fun getCount(): Int = TITLES.size
}