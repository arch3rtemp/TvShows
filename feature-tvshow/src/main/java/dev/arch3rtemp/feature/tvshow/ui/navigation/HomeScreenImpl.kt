package dev.arch3rtemp.feature.tvshow.ui.navigation

import androidx.fragment.app.Fragment
import dev.arch3rtemp.feature.tvshow.ui.home.HomeFragment
import dev.arch3rtemp.navigation.screens.HomeScreen

class HomeScreenImpl : HomeScreen {
    override var destinationFragment: Fragment? = null

    override val tag = HomeFragment.TAG

    override fun destination() {
        destinationFragment = HomeFragment.newInstance()
    }
}