package com.mmteams91.todoapp.core.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.support.annotation.AttrRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet


fun Context.resolveSizeAttribute(@AttrRes attrId: Int): Int {
    val a = obtainStyledAttributes(intArrayOf(attrId))
    val size = a.getDimensionPixelSize(0, -1)
    a.recycle()
    return size
}

fun Context.resolveDrawable(@AttrRes attrId: Int): Drawable? {
    val a = obtainStyledAttributes(intArrayOf(attrId))
    val drawable = a.getDrawable(0)
    a.recycle()
    return drawable
}

fun Context.resolveColor(@AttrRes attrId: Int): Int {
    val a = obtainStyledAttributes(intArrayOf(attrId))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun Context.resolveColor(attrs: AttributeSet, @AttrRes attrId: Int): Int {
    val a = obtainStyledAttributes(attrs, intArrayOf(attrId))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun Context.isPermissionGranted(permission: String) = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED