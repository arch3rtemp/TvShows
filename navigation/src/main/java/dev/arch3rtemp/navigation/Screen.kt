package dev.arch3rtemp.navigation

import androidx.fragment.app.Fragment

interface Screen {
    var destinationFragment: Fragment?
    val tag: String? get() = null
}