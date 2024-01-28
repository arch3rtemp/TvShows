package dev.arch3rtemp.navigation

interface Navigator {

    fun navigateWithReplaceTo(
        screen: Screen,
        allowingStateLoss : Boolean = true,
        addToBackStack: Boolean = true
    ): Boolean?

    fun navigateWithReplaceTo(
        screen: Screen,
        allowingStateLoss : Boolean = true,
        addToBackStack: Boolean = true,
        allowReordering: Boolean = false
    ): Boolean?

    fun replaceRootScreen(
        screen: Screen,
        allowingStateLoss : Boolean = true,
        addToBackStack: Boolean = true,
    ): Boolean?

    fun navigateWithAddTo(
        screen: Screen,
        clearBackStack: Boolean = false,
        allowingStateLoss : Boolean = true,
        addToBackStack: Boolean = true,
    ): Boolean?

    fun executePendingTransactions()

    fun backTo(tag: String)

    fun back()
}