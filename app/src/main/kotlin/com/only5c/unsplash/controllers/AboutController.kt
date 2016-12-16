package com.only5c.unsplash.controllers

import android.view.MenuItem
import android.view.View
import com.marcoscg.easylicensesdialog.EasyLicensesDialog
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
        view.legal_stuff.onClick {
            val easyLicensesDialog = EasyLicensesDialog(activity)
            easyLicensesDialog.setTitle("Licenses")
            easyLicensesDialog.setCancelable(true)
            easyLicensesDialog.show()
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) {
            android.R.id.home -> activity.finish()
        }
    }
}