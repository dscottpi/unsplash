package com.only5c.unsplash.models

import com.google.gson.annotations.SerializedName

class UserLinks {
    @SerializedName("self") var self: String? = null
    @SerializedName("html") var htmlLink: String? = null
    @SerializedName("photos") var photos: String? = null
    @SerializedName("likes") var likes: String? = null
    @SerializedName("portfolio") var portfolio: String? = null
    @SerializedName("following") var following: String? = null
    @SerializedName("followers") var followers: String? = null
}