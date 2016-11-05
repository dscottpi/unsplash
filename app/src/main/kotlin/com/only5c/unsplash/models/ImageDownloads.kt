package com.only5c.unsplash.models

import com.google.gson.annotations.SerializedName

class ImageDownloads {
    @SerializedName("self") var self: String? = null
    @SerializedName("html") var htmlLink: String? = null
    @SerializedName("download") var downloadUrl: String? = null
    @SerializedName("download_location") var downloadLocation: String? = null
}