package com.only5c.unsplash.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
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

fun Context.openTwitter() {
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/dscottpi")))
}

fun Context.openSupportEmail() {
    val intent = Intent(android.content.Intent.ACTION_SEND)
    intent.type = "plain/text"
    intent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf("onlyfivec@gmail.com"))
    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Aperture - Feedback")
    this.startActivity(Intent.createChooser(intent, "Send email..."))
}
