package com.mmteams91.todoapp.app

import android.support.v4.app.Fragment
import com.mmteams91.todoapp.features.project.ProjectsFragment
import com.mmteams91.todoapp.features.user.auth.AuthFragment


sealed class Screen(val addToBackStack: Boolean = true, val isRoot: Boolean = false) {
    abstract fun newInstance(): Fragment

    open fun name(): String = javaClass.simpleName

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

class AuthScreen : Screen(false, true) {
    override fun newInstance(): Fragment = AuthFragment.newInstance()
}

class ProjectsScreen :Screen(false,true){
    override fun newInstance(): Fragment =ProjectsFragment.newInstance()
}
