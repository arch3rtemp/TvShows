package dev.arch3rtemp.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import javax.inject.Inject

class NavigatorImpl @Inject constructor(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : Navigator {

    override fun navigateWithReplaceTo(
        screen: Screen,
        allowingStateLoss: Boolean,
        addToBackStack: Boolean
    ): Boolean? {

        return navigateWithReplaceTo(
            screen,
            allowingStateLoss = allowingStateLoss,
            addToBackStack = addToBackStack,
            allowReordering = false
        )
    }

    override fun navigateWithReplaceTo(
        screen: Screen,
        allowingStateLoss: Boolean,
        addToBackStack: Boolean,
        allowReordering: Boolean
    ): Boolean? {

        return navigate(
            screen = screen,
            allowingStateLoss = allowingStateLoss,
            addToBackStack = addToBackStack,
            clearBackStack = false,
            replaceScreen = true,
            allowReordering = allowReordering
        )

    }

    /**
     * Clear all fragments and open new one as root.
     */
    override fun replaceRootScreen(
        screen: Screen,
        allowingStateLoss: Boolean,
        addToBackStack: Boolean
    ): Boolean? {

        return navigate(
            screen = screen,
            allowingStateLoss = allowingStateLoss,
            addToBackStack = addToBackStack,
            clearBackStack = true,
            replaceScreen = true
        )

    }

    override fun navigateWithAddTo(
        screen: Screen,
        clearBackStack: Boolean,
        allowingStateLoss: Boolean,
        addToBackStack: Boolean
    ): Boolean? {

        return navigate(
            screen = screen,
            clearBackStack = clearBackStack,
            allowingStateLoss = allowingStateLoss,
            addToBackStack = addToBackStack,
            replaceScreen = false
        )

    }

    override fun executePendingTransactions() {
        fragmentManager.executePendingTransactions()
    }

    private fun navigate(
        screen: Screen,
        clearBackStack: Boolean,
        allowingStateLoss: Boolean,
        addToBackStack: Boolean,
        replaceScreen: Boolean,
        allowReordering: Boolean = false
    ): Boolean? {

        if (screen.destinationFragment != null) {

            if (isScreenLastOnTheCurrentStack(containerId, screen.destinationFragment!!)) {
                return true
            }

            if (clearBackStack) {
                fragmentManager.clearBackStack()
            }

            fragmentManager.commit(
                allowStateLoss = allowingStateLoss
            ) {

                if (replaceScreen) {
                    replace(containerId, screen.destinationFragment!!, screen.tag)

                } else {
                    add(containerId, screen.destinationFragment!!, screen.tag)

                }

                if (addToBackStack) {
                    addToBackStack(screen.tag)
                }

                if (allowReordering)
                    setReorderingAllowed(true)
            }
        }

        return null
    }

    private fun isScreenLastOnTheCurrentStack(
        containerId: Int, fragment: Fragment
    ): Boolean {
        val currentFragment: Fragment? = fragmentManager.findFragmentById(
            containerId
        )

        val name = fragment.javaClass.simpleName

        return name == currentFragment?.javaClass?.simpleName
    }

    override fun backTo(tag: String) {

        getFragmentManager().popBackStack(tag, 0)
    }

    override fun back() {
        getFragmentManager().popBackStackImmediate()
    }

    private fun getFragmentManager() = fragmentManager
}