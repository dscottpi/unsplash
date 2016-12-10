package com.only5c.unsplash.controllers

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.only5c.unsplash.activities.BaseActivity

abstract class BaseController(val activity: BaseActivity, val view: View) {
    open fun initView() {}
    open fun pause() {}
    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
    open fun onPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {}
    open fun onMenuItemSelected(menuItem: MenuItem) {}
    open fun drawerItemSelected(position: Int) {}
    open fun filterSearch(text: String) {}
    open fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {}
    open fun onMenuCreated(menu: Menu) {}
}