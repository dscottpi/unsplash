package com.only5c.unsplash.activities

import android.content.Context
import android.content.Intent
import com.only5c.unsplash.R
import com.only5c.unsplash.controllers.search.SearchController

class SearchActivity : BaseActivity() {
    override var layoutResource: Int? = R.layout.activity_search
    override var toolbarTitle: Int? = R.string.blank
    override var toolbarPadding: Boolean? = false
    override var menuResource: Int? = R.menu.menu_default
    override var toolbarIcon: Int? = R.drawable.ic_back

    override fun initController() {
        controllers.add(SearchController(this, view!!))
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }
}