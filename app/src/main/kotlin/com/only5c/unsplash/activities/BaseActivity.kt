package com.only5c.unsplash.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks
import com.github.ksoichiro.android.observablescrollview.ScrollState
import com.only5c.unsplash.R
import com.only5c.unsplash.adapters.DrawerAdapter
import com.only5c.unsplash.api.UnsplashApi
import com.only5c.unsplash.controllers.BaseController
import com.only5c.unsplash.extensions.createTypeface
import com.only5c.unsplash.models.DrawerItem
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.onItemClick
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

abstract class BaseActivity : AppCompatActivity(), ObservableScrollViewCallbacks {
    open var layoutResource: Int? = null
    open var toolbarTitle: Int? = R.string.app_name
    open var toolbarIcon: Int? = R.drawable.ic_menu
    open var toolbarPadding: Boolean? = false
    open var menuResource: Int? = R.menu.menu_default
    open var theme: Int? = R.style.AppTheme

    var view: View? = null
    var toolbar: Toolbar? = null
    var controllers = ArrayList<BaseController>()

    var api: UnsplashApi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource!!)
        setTheme(theme!!)
        view = findViewById(android.R.id.content)
        //Fabric.with(this, Crashlytics())
        //Fabric.with(this, Answers())
        initToolbar()
        initController()
        initApi()
        controllers.forEach { it.initView() }
    }

    override fun onPause() {
        super.onPause()
        controllers.forEach { it.pause() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menuResource!!, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        controllers.forEach { it.onMenuItemSelected(item!!) }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        controllers.forEach { it.onActivityResult(requestCode, resultCode, data) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        controllers.forEach {  }
    }

    open fun initController() {}

    fun initToolbar() {
        val padding = if (toolbarPadding == true) { statusBarHeight } else { 0 }
        toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar!!.setNavigationIcon(toolbarIcon!!)
        toolbar!!.setPadding(0, padding, 0, 0)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(toolbarTitle!!)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    fun changeTabsFont() {
        val vg = page_indicator.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0..tabsCount - 1) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0..tabChildsCount - 1) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.setTypeface(this.createTypeface("courier.ttf"))
                    tabViewChild.setTextColor(getColor(android.R.color.black))
                }
            }
        }
    }

    fun initApi() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        api = retrofit.create(UnsplashApi::class.java)
    }

    fun initDrawerLayout(data: ArrayList<DrawerItem>) {
        val adapter = DrawerAdapter(this)
        adapter.data = data
        drawer_content.adapter = adapter
        drawer_content.onItemClick { adapterView, view, position, id ->
            controllers.forEach { it.drawerItemSelected(position) }
        }

        drawer_layout.setDrawerListener(getDrawerToggle())
    }

    fun getDrawerToggle() : ActionBarDrawerToggle {
        val drawerToggle = object : ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_closed) {

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                drawer_layout.fitsSystemWindows = true
                invalidateOptionsMenu()
                syncState()
                super.onDrawerSlide(drawerView, 0f)
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                drawer_layout.fitsSystemWindows = false
                invalidateOptionsMenu()
                syncState()
            }

            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                super.onDrawerSlide(drawerView, 0f)
            }
        }

        drawerToggle.syncState()
        drawerToggle.isDrawerIndicatorEnabled = true

        return drawerToggle
    }

    override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
        controllers.forEach { it.onScrollChanged(scrollY, firstScroll, dragging) }
    }

    override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {}
    override fun onDownMotionEvent() {}

    val statusBarHeight: Int
        get() {
            var result = 0
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
}