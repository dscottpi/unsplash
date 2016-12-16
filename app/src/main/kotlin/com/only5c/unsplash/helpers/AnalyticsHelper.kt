package com.only5c.unsplash.helpers

import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.crashlytics.android.answers.SearchEvent
import com.only5c.unsplash.*

fun logImageOpened() {
    Answers.getInstance().logCustom(CustomEvent(IMAGE_OPENED))
}

fun logLikeButtonClicked() {
    Answers.getInstance().logCustom(CustomEvent(LIKE_BUTTON))
}

fun logDownloadButtonClicked() {
    Answers.getInstance().logCustom(CustomEvent(DOWNLOAD_BUTTON))
}

fun logProfileViewed() {
    Answers.getInstance().logCustom(CustomEvent(PROFILE_VIEW))
}

fun logSearchOpened() {
    Answers.getInstance().logCustom(CustomEvent(SEARCH))
}

fun logSearch(query: String) {
    Answers.getInstance().logSearch(SearchEvent().putQuery(query))
}

fun logWallpaperClicked() {
    Answers.getInstance().logCustom(CustomEvent(WALLPAPER_BUTTON))
}