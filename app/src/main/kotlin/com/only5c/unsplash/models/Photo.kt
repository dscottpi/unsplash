package com.only5c.unsplash.models

import android.net.Uri
import com.google.gson.annotations.SerializedName

class Photo {
    @SerializedName("id") var id: String? = null
    @SerializedName("user") var user: User? = null
    @SerializedName("urls") var image: Image? = null
    @SerializedName("links") var downloads: ImageDownloads? = null
    @SerializedName("likes") var likes: Int? = null
    @SerializedName("width") var width: Int? = null
    @SerializedName("height") var height: Int? = null

    fun getNormalWidth() : Int {
        image?.regular?.let {
            try {
                val w = Integer.parseInt(Uri.parse(image?.regular!!).getQueryParameter("w"))
                return if (w == 0) {
                    1080
                } else {
                    w
                }
            } catch (e: NumberFormatException) {
                return 1080
            }
        }

        return 1080
    }

    fun getNormalHeight() : Int {
        val w = getNormalWidth()
        height?.let {
            return (1.0 * height!! * w / width!!).toInt()
        }

        return 200
    }
}
