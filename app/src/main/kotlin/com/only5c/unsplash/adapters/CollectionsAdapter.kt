package com.only5c.unsplash.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.only5c.unsplash.*
import com.only5c.unsplash.activities.CollectionActivity
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.models.Collection
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.collections_item.view.*
import org.jetbrains.anko.onClick

class CollectionsAdapter(val context: Context) : RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {

    var data: List<Collection> = listOf(Collection())
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cover: ImageView = view.cover_photo
        val title: TextView = view.collection_title
        val photos: TextView = view.num_photos
        val avatar: ImageView = view.avatar
        val card: CardView = view.collection_card
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val collection = data[position]
        Picasso.with(context).load(collection.coverPhoto?.image?.small).into(holder!!.cover)

        holder.title.text = collection.title
        holder.photos.text = "${collection.totalPhotos} photos"
        holder.card.onClick { startCollectionActivity(collection) }

        Picasso.with(context).load(collection.user?.profileImage?.medium).into(holder.avatar)
    }

    private fun startCollectionActivity(collection: Collection) {
        val intent = Intent(context, CollectionActivity::class.java)
        intent.putExtra(COLLECTION_ID_EXTRA, collection.id)
        intent.putExtra(COLLECTION_COVER_EXTRA, collection.coverPhoto?.image?.regular)
        intent.putExtra(COLLECTION_TITLE_EXTRA, collection.title)
        intent.putExtra(COLLECTION_SIZE_EXTRA, collection.totalPhotos)
        intent.putExtra(COLLECTION_AUTHOR, collection.user?.name)
        context.startActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = context.inflate(R.layout.collections_item)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
}