package com.only5c.unsplash.controllers.search

import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import com.only5c.unsplash.R
import com.only5c.unsplash.activities.BaseActivity
import com.only5c.unsplash.adapters.SearchAdapter
import com.only5c.unsplash.controllers.BaseController
import com.only5c.unsplash.extensions.closeKeyboard
import com.only5c.unsplash.extensions.openKeyboard
import com.only5c.unsplash.extensions.setUnderlineColor
import kotlinx.android.synthetic.main.activity_search.view.*
import kotlinx.android.synthetic.main.search_toolbar.view.*

class SearchController(activity: BaseActivity, view: View) : BaseController(activity, view) {
    var adapter: SearchAdapter? = SearchAdapter(activity, activity.supportFragmentManager)

    override fun initView() {
        view.search_pager.adapter = adapter
        view.search_page_indicator.setViewPager(view.search_pager)
        view.search.setUnderlineColor(activity, R.color.colorAccentDark)
        view.search.setOnEditorActionListener { textView, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val photos = adapter!!.instantiateItem(view.search_pager, 0) as SearchImageListController
                    val users = adapter!!.instantiateItem(view.search_pager, 1) as SearchUserListController
                    photos.search = textView.text.toString()
                    users.search = textView.text.toString()
                    activity.closeKeyboard(view)
                    true
                } else -> true
            }
        }
        activity.openKeyboard(view.search)
        view.search.requestFocus()
    }

    override fun onMenuItemSelected(menuItem: MenuItem) {
        when(menuItem.itemId) {
            android.R.id.home -> activity.finish()
        }
    }
}