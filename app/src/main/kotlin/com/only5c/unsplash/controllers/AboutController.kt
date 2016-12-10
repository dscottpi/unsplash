package com.only5c.unsplash.controllers

import android.view.View
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.extensions.createTypeface
import com.only5c.unsplash.extensions.openSupportEmail
import com.only5c.unsplash.extensions.openTwitter
import kotlinx.android.synthetic.main.activity_about.view.*
import org.jetbrains.anko.onClick

class AboutController(activity: BaseActivity, view: View) : BaseController(activity, view) {

    override fun initView() {
        val courier = activity.createTypeface("courier.ttf")
        view.user_location.typeface = courier
        view.user_bio.typeface = courier
        view.twitter.onClick { activity.openTwitter() }
        view.email.onClick { activity.openSupportEmail() }
    }
}