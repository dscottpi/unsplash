package com.only5c.unsplash.models

import com.google.gson.annotations.SerializedName

class PhotoSearch {
    @SerializedName("total_pages") var totalPages: Int? = null
    @SerializedName("results") var photos: List<Photo>? = null
}