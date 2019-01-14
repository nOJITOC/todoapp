package com.mmteams91.todoapp.features.navigator

import android.os.Parcelable
import android.support.v4.app.Fragment

interface IScreen:Parcelable {
    val addToBackStack: Boolean
    val isRoot: Boolean
    fun name():String
    fun newInstance(): Fragment
}