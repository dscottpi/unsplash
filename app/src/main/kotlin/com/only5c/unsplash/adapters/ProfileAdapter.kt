package com.only5c.unsplash.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.controllers.home.HomeImageListController
import com.only5c.unsplash.controllers.profile.UserLikesImageListController
import com.only5c.unsplash.controllers.profile.UserPhotosImageListController

class ProfileAdapter(val profileView: View, val userName: String, val activity: BaseActivity, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val TITLES = arrayOf("photos", "likes")

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val controller = UserPhotosImageListController()
                return controller
            }

            1 -> {
                val controller = UserLikesImageListController()
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