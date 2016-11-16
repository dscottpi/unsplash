package com.only5c.unsplash.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.widget.EditText

fun EditText.setUnderlineColor(context: Context, color: Int) {
    this.background.mutate().setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_ATOP)
}