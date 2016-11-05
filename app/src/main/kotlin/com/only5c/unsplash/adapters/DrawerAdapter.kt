package com.only5c.unsplash.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.only5c.unsplash.extensions.createTypeface
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.DrawerItem
import kotlinx.android.synthetic.main.drawer_item.view.*
import java.util.*

class DrawerAdapter(val context: Context) : BaseAdapter() {
    var data = ArrayList<DrawerItem>()
        set(value) {
            field = value; notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val drawerItem = data[position]
        val view = context.inflate(drawerItem.layout!!, parent)

        view.drawer_item_text?.let { it.typeface = context.createTypeface("courier.ttf") }
        drawerItem.title?.let { view.drawer_item_text.text = drawerItem.title }

        return view
    }

    override fun getItem(position: Int): Any = data[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int = data.size
}

