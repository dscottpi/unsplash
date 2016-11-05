package com.only5c.unsplash.adapters

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.only5c.unsplash.R
import com.only5c.unsplash.activities.ProfileActivity
import com.only5c.unsplash.extensions.createTypeface
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.user_list_item.view.*
import org.jetbrains.anko.onClick

class UserListAdapter(val context: Context) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    var courier: Typeface? = null
    init { courier = context.createTypeface("courier.ttf") }

    var data: List<User> = listOf(User())
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val avatar: CircleImageView = view.avatar
        val name: TextView = view.name
        val username: TextView = view.username
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val user = data[position]
        holder!!.name.text = user.name
        holder.username.typeface = courier
        holder.username.text = "@${user.username}"
        Picasso.with(context).load(user.profileImage?.medium).into(holder.avatar)
        holder.view.onClick { ProfileActivity.launch(context, user.username!!) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = context.inflate(R.layout.user_list_item, parent)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
}