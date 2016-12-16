package com.only5c.unsplash.extensions

import android.content.Context

fun Int.toDp(context: Context) : Int {
    return (this / context.resources.displayMetrics.density).toInt()
}