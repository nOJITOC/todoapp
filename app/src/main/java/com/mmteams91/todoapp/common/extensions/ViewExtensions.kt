package com.mmteams91.todoapp.common.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

fun View.show() {
    if (visibility != View.VISIBLE) {
        post { visibility = View.VISIBLE }
    }
}

fun View.hide() {
    if (visibility != View.GONE) {
        post { visibility = View.GONE }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(this.context).inflate(layoutRes, this, attachToRoot)

fun View.setVisible(isVisible: Boolean) = if (isVisible) show() else hide()

fun TextView.stringText() = text?.toString() ?: ""
