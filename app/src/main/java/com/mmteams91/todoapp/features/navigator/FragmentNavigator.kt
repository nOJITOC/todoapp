package com.mmteams91.todoapp.features.navigator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.mmteams91.todoapp.app.SCREEN_KEY
import com.mmteams91.todoapp.common.extensions.transact


class FragmentNavigator(
        private val fragmentManager: FragmentManager,
        private val containerId: Int
) : INavigator {

    private var root: IScreen? = null

    override fun navigateTo(screen: IScreen) {
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

    private fun addScreen(screen: IScreen) {
        val fragment = screen.newInstance()
        fragment.screen = screen
        fragmentManager.transact {
            replace(containerId, screen.newInstance())
            if (screen.addToBackStack) {
                addToBackStack(screen.name())
            }
        }
    }

    private fun isInStack(screenName: String): Boolean {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            if (fragmentManager.getBackStackEntryAt(i).name == screenName) {
                return true
            }
        }
        return false
    }

    private fun addRoot(screen: IScreen) {
        if (screen == root) {
            if (fragmentManager.findFragmentById(containerId)?.screen != screen)
                fragmentManager.popBackStack(null, 0)
        } else {
            root = screen
            fragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
            addScreen(screen)
        }
    }
}

var Fragment.screen: IScreen?
    get() = arguments?.getParcelable(SCREEN_KEY)
    set(value) {
        (arguments ?: Bundle().also { arguments = it }).putParcelable(SCREEN_KEY, value)
    }