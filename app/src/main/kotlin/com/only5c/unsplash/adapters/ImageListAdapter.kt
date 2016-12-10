package com.only5c.unsplash.adapters

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.only5c.unsplash.R
import com.only5c.unsplash.activities.ImageViewActivity
import com.only5c.unsplash.activities.ProfileActivity
import com.only5c.unsplash.extensions.canWriteExternalStorage
import com.only5c.unsplash.extensions.createTypeface
import com.only5c.unsplash.extensions.inflate
import com.only5c.unsplash.helpers.downloadPhoto
import com.only5c.unsplash.models.Photo
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.image_list_item.view.*
import org.jetbrains.anko.onClick

class ImageListAdapter(val activity: AppCompatActivity) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {
    var courier: Typeface? = null
    init { courier = activity.createTypeface("courier.ttf") }

    var data: List<Photo> = listOf(Photo())
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.image
        val avatar: CircleImageView = view.avatar
        val username: TextView = view.username
        val likes: TextView = view.likes
        val likeButton: ImageButton = view.like
        val download: ImageButton = view.download
    }

    override fun onBindViewHolder(holder: ImageListAdapter.ViewHolder?, position: Int) {
        val photo = data[position]
        holder!!.avatar.setImageBitmap(null)
        holder.image.setImageBitmap(null)

        photo.height?.let { holder.image.minimumHeight = it }
        Picasso.with(activity)
                .load(photo.image?.small)
                .tag(activity)
                .into(holder.image)

        Picasso.with(activity).load(photo.user?.profileImage?.medium).into(holder.avatar)

        holder.username.typeface = courier
        holder.likes.typeface = courier

        holder.username.text = photo.user?.name
        holder.likes.text = "${photo.likes} likes"

        holder.image.onClick { ImageViewActivity.launch(activity, photo.id!!) }

        holder.likeButton.onClick { holder.likeButton.setImageResource(R.drawable.ic_like) }
        holder.avatar.onClick { ProfileActivity.launch(activity, photo.user!!.username!!) }
        holder.username.onClick { ProfileActivity.launch(activity, photo.user!!.username!!) }
        holder.download.onClick {
            activity.canWriteExternalStorage {
                downloadPhoto(activity, photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = activity.inflate(R.layout.image_list_item)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
}