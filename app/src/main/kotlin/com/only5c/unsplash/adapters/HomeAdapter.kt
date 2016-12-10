package com.only5c.unsplash.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.controllers.collections.CollectionsController
import com.only5c.unsplash.controllers.home.HomeImageListController
import com.only5c.unsplash.controllers.home.NewImageListController

class HomeAdapter(val activity: BaseActivity, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val TITLES = arrayOf("home", "new", "collections")

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val controller = HomeImageListController()
                return controller
            }

            1 -> {
                val controller = NewImageListController()
                return controller
            }

            2 -> {
                return CollectionsController()
            }
        }

        val controller = HomeImageListController()
        return controller
    }

    override fun getPageTitle(position: Int): CharSequence = TITLES[position]

    override fun getCount(): Int = TITLES.size
}