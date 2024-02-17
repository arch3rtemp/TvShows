package dev.arch3rtemp.navigation

import androidx.fragment.app.FragmentManager

fun FragmentManager.clearBackStack() {

    if (backStackEntryCount > 0) {
        val first: FragmentManager.BackStackEntry = getBackStackEntryAt(0)
        popBackStackImmediate(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

/**
 * @param name of transaction we want return to
 */
fun FragmentManager.returnToBackStackEntry(name: String) {

    if (backStackEntryCount > 0) {
        popBackStack(name, 0)
    }
}

fun FragmentManager.isBackStackContain(name: String): Boolean {
    for (entry in 0 until backStackEntryCount) {
        if (getBackStackEntryAt(entry).name.equals(name)) {
            return true
        }
    }

    return false
}