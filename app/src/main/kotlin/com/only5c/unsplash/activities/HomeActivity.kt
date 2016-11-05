package com.only5c.unsplash.activities

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.github.ksoichiro.android.observablescrollview.ScrollUtils
import com.only5c.unsplash.R
import com.only5c.unsplash.controllers.home.HomeController
import kotlinx.android.synthetic.main.activity_home.view.*

class HomeActivity : BaseActivity(), ObservableScrollViewCallbacks {
    override var layoutResource: Int? = R.layout.activity_home
    override var toolbarTitle: Int? = R.string.app_name
    override var toolbarPadding: Boolean? = false
    override var menuResource: Int? = R.menu.menu_home
    var baseTranslationY: Int? = 0

    override fun initController() {
        controllers.add(HomeController(this, view!!))
    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        if (dragging) {
            val toolbarHeight = view!!.toolbar.height
            if (firstScroll) {
                val currentHeaderTranslationY = view!!.page_indicator.translationY
                if (-toolbarHeight < currentHeaderTranslationY) {
                    baseTranslationY = scrollY
                }
            }

            val headerTranslationY = ScrollUtils.getFloat(-(scrollY - baseTranslationY!!.toFloat()), -toolbarHeight.toFloat(), 0f)
            view!!.header.animate().cancel()
            view!!.header.animate().translationY(headerTranslationY).setDuration(200).start()
        }
    }

    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
        if (view!!.page_indicator.height.toFloat() != -view!!.toolbar.translationY) {
            view!!.page_indicator.animate().cancel()
            view!!.toolbar.animate().translationY(0f).setDuration(200).start()
        }
    }

    override fun onDownMotionEvent() {
    }
}