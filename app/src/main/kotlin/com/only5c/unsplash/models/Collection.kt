package com.only5c.unsplash.models

import com.google.gson.annotations.SerializedName

class Collection {
    @SerializedName("id") var id: Int? = null
    @SerializedName("title") var title: String? = null
    @SerializedName("description") var desc: String? = null
    @SerializedName("total_photos") var totalPhotos: Int? = null
    @SerializedName("share_key") var shareKey: String? = null
    @SerializedName("cover_photo") var coverPhoto: Photo? = null
    @SerializedName("user") var user: User? = null
}