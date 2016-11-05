package com.only5c.unsplash.helpers

import com.only5c.unsplash.R
import com.only5c.unsplash.models.DrawerItem
import java.util.*

fun getHomeDrawerContent() : ArrayList<DrawerItem> {
    val items = ArrayList<DrawerItem>()
    val header = DrawerItem()
    header.layout = R.layout.drawer_header
    items.add(header)

    val item1 = DrawerItem()
    item1.layout = R.layout.drawer_item_plain_text
    item1.title = "Explore"
    items.add(item1)

    val item2 = DrawerItem()
    item2.layout = R.layout.drawer_item_plain_text
    item2.title = "Made with Unsplash"
    items.add(item2)

    val item3 = DrawerItem()
    item3.layout = R.layout.drawer_item_plain_text
    item3.title = "Events"
    items.add(item3)

    val item4 = DrawerItem()
    item4.layout = R.layout.drawer_item_plain_text
    item4.title = "Settings"
    items.add(item4)

    return items
}