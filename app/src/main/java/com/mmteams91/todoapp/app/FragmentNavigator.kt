package com.mmteams91.todoapp.app

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.mmteams91.todoapp.core.extensions.transact

class FragmentNavigator(
        private val fragmentManager: FragmentManager,
        private val containerId: Int
) : Navigator {

    private var root: Screen? = null

    override fun navigateTo(screen: Screen) {
        if (screen.isRoot) {
            addRoot(screen)
            return
        }
        val name = screen.name()
        if (isInStack(name)) {
            fragmentManager.popBackStack(name, if (screen.addToBackStack) 0 else POP_BACK_STACK_INCLUSIVE)
            if (!screen.addToBackStack) {
                addScreen(screen)
            }
        } else {
            addScreen(screen)
        }
    }

    private fun addScreen(screen: Screen) {
        fragmentManager.transact {
            replace(containerId, screen.newInstance())
            if (screen.addToBackStack) {
                addToBackStack(screen.name())
            }
        }
    }

    fun isInStack(screenName: String): Boolean {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            if (fragmentManager.getBackStackEntryAt(i).name == screenName) {
                return true
            }
        }
        return false
    }

    fun getFirstInStackOrNull(): String? {
        return if (fragmentManager.backStackEntryCount == 0) null else fragmentManager.getBackStackEntryAt(0).name
    }

    private fun addRoot(screen: Screen) {
        if (screen.name() == root?.name()) {
            fragmentManager.popBackStack(screen.name(), 0)
        } else {
            root = screen
            fragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
            addScreen(screen)
        }
    }

}