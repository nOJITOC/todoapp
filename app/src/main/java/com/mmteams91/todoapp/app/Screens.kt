package com.mmteams91.todoapp.app

import android.support.v4.app.Fragment
import com.mmteams91.todoapp.features.user.auth.AuthFragment


sealed class Screen(val addToBackStack: Boolean = true) {
    abstract fun newInstance(): Fragment

    open fun name(): String = javaClass.simpleName
}

class AuthScreen : Screen(false) {
    override fun newInstance(): Fragment = AuthFragment.newInstance()
}
