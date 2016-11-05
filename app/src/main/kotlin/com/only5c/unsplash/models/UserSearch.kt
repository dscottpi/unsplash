package com.only5c.unsplash.models

import com.google.gson.annotations.SerializedName

class UserSearch {
    @SerializedName("results") var users: List<User>? = null
}