package com.only5c.unsplash.activities

import android.content.Context
import android.content.Intent
import com.only5c.unsplash.R
import com.only5c.unsplash.controllers.AboutController

class AboutActivity : BaseActivity() {
    override var toolbarIcon: Int? = R.drawable.ic_back
    override var toolbarTitle: Int? = R.string.about
    override var layoutResource: Int? = R.layout.activity_about

    override fun initController() {
        controllers.add(AboutController(this, view!!))
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(Intent(context, AboutActivity::class.java))
        }
    }
}