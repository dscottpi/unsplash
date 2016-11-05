package com.only5c.unsplash.models

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("id") var id: String? = null
    @SerializedName("username") var username: String? = null
    @SerializedName("name") var name: String? = null
    @SerializedName("bio") var bio: String? = null
    @SerializedName("location") var location: String? = null
    @SerializedName("total_likes") var totalLikes: Int? = null
    @SerializedName("total_photos") var totalPhotos: Int? = null
    @SerializedName("total_collections") var totalCollections: Int? = null
    @SerializedName("profile_image") var profileImage: UserProfileImage? = null
    @SerializedName("links") var links: UserLinks? = null
    @SerializedName("portfolio_url") var portfolioUrl: String? = null
}