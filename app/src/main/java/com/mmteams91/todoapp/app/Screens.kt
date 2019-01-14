package com.mmteams91.todoapp.app

import android.support.v4.app.Fragment
import com.mmteams91.todoapp.BuildConfig
import com.mmteams91.todoapp.features.agenda.AgendaFragment
import com.mmteams91.todoapp.features.navigator.IScreen
import com.mmteams91.todoapp.features.user.auth.AuthFragment
import kotlinx.android.parcel.Parcelize

const val SCREEN_KEY = BuildConfig.APPLICATION_ID + ".screen"

sealed class Screen(
        override val addToBackStack: Boolean = true,
        override val isRoot: Boolean = false
) : IScreen{
    override fun name(): String = javaClass.simpleName

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Screen

        if (addToBackStack != other.addToBackStack) return false
        if (isRoot != other.isRoot) return false

        return true
    }

    override fun hashCode(): Int {
        var result = addToBackStack.hashCode()
        result = 31 * result + isRoot.hashCode()
        return result
    }
}

@Parcelize
class AuthScreen : Screen(false, true) {
    override fun newInstance(): Fragment = AuthFragment.newInstance()
}

@Parcelize
class AgendaScreen : Screen(false, true) {
    override fun newInstance(): Fragment = AgendaFragment.newInstance()
}

