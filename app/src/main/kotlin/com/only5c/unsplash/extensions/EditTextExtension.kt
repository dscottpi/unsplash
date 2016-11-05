package com.only5c.unsplash.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.widget.EditText

fun EditText.setUnderlineColor(context: Context, color: Int) {
    this.background.mutate().setColorFilter(context.resources.getColor(color), PorterDuff.Mode.SRC_ATOP)
}