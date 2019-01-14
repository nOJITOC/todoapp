package com.mmteams91.todoapp.common.extensions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.mmteams91.todoapp.R

fun AppCompatActivity.showFragment(fragment: Fragment, addToBackStack: Boolean = true) {
    supportFragmentManager.transact {
        replace(R.id.container, fragment)
        if (addToBackStack) {
            addToBackStack(null)
        }
    }
}
