package com.only5c.unsplash.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.only5c.unsplash.R
import com.only5c.unsplash.activities.ImageViewActivity
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.collection_item.view.*
import org.jetbrains.anko.onClick

class CollectionAdapter(val context: Context) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    var data: List<Photo> = listOf(Photo())
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.collection_image
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val photo = data[position]
        Picasso.with(context).load(photo.image?.thumb).into(holder!!.image)
        holder.image.onClick {
            ImageViewActivity.launch(context, photo.id!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = context.inflate(R.layout.collection_item)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
}