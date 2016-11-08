package com.only5c.unsplash.activities

import android.os.Bundle
import com.only5c.unsplash.R
import com.only5c.unsplash.controllers.home.HomeController

class HomeActivity : BaseActivity() {
    override var layoutResource: Int? = R.layout.activity_home
    override var toolbarTitle: Int? = R.string.app_name
    override var toolbarPadding: Boolean? = false
    override var menuResource: Int? = R.menu.menu_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setHomeButtonEnabled(false)
    }

    override fun initController() {
        controllers.add(HomeController(this, view!!))
    }
}