package com.only5c.unsplash.controllers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.only5c.unsplash.R
import com.only5c.unsplash.extensions.inflate

class CollectionsController : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context.inflate(R.layout.collections)
    }
}