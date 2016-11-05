package com.only5c.unsplash.models

import com.google.gson.annotations.SerializedName

class UserProfileImage {
    @SerializedName("small") var small: String? = null
    @SerializedName("medium") var medium: String? = null
    @SerializedName("large") var large: String? = null
}