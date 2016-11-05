package com.only5c.unsplash.helpers

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.only5c.unsplash.models.Photo

fun downloadPhoto(context: Context, photo: Photo) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(photo.downloads?.downloadUrl))
    request.setTitle("${photo.id}.jpg")
    request.setDescription("Downloaded photo from Unsplash")
    request.setVisibleInDownloadsUi(true)
    request.setMimeType("image/jpeg")
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${photo.id}.jpg")
    downloadManager.enqueue(request)
}