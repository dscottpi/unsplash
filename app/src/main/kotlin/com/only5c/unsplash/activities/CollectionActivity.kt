package com.only5c.unsplash.activities

import com.only5c.unsplash.R
import com.only5c.unsplash.controllers.collections.CollectionController

class CollectionActivity : BaseActivity() {
    override var layoutResource: Int? = R.layout.activity_collection
    override var toolbarTitle: Int? = R.string.blank
    override var toolbarPadding: Boolean? = true
    override var toolbarIcon: Int? = R.drawable.ic_back_white
    override var theme: Int? = R.style.AppTheme_TransStatus

    override fun initController() {
        val controller = CollectionController(this, view!!)
        controllers.add(controller)
    }
}