package com.only5c.unsplash.extensions

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

fun Context.inflate(layoutResource: Int, parent : ViewGroup? = null) : View {
    return LayoutInflater.from(this).inflate(layoutResource, parent, false)
}

fun Context.openKeyboard(view: View) {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.toggleSoftInputFromWindow(view.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
}

fun Context.closeKeyboard(view: View) {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.createTypeface(filePath: String) : Typeface {
    return Typeface.createFromAsset(this.assets, filePath)
}
