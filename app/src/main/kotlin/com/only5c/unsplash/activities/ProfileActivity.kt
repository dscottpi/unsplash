package com.only5c.unsplash.activities

import android.content.Context
import android.content.Intent
import com.only5c.unsplash.R
import com.only5c.unsplash.USER_NAME_EXTRA
import com.only5c.unsplash.controllers.profile.ProfileController

class ProfileActivity : BaseActivity() {
    override var layoutResource: Int? = R.layout.activity_profile
    override var toolbarTitle: Int? = R.string.blank
    override var toolbarPadding: Boolean? = false
    override var toolbarIcon: Int? = R.drawable.ic_back

    override fun initController() {
        val userName = intent.getStringExtra(USER_NAME_EXTRA)
        controllers.add(ProfileController(userName, this, view!!))
        toolbar!!.setBackgroundColor(android.R.color.transparent)
    }

    companion object {
        fun launch(context: Context, userName: String) {
            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra(USER_NAME_EXTRA, userName)
            context.startActivity(intent)
        }
    }
}