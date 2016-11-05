package com.only5c.unsplash.models

import com.google.gson.annotations.SerializedName

class Photo {
    @SerializedName("id") var id: String? = null
    @SerializedName("user") var user: User? = null
    @SerializedName("urls") var image: Image? = null
    @SerializedName("links") var downloads: ImageDownloads? = null
    @SerializedName("likes") var likes: Int? = null
    @SerializedName("width") var width: Int? = null
    @SerializedName("height") var height: Int? = null
}
