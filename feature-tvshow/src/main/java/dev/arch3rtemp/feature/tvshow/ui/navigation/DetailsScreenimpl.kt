package dev.arch3rtemp.feature.tvshow.ui.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.arch3rtemp.feature.tvshow.ui.detail.DetailFragment
import dev.arch3rtemp.navigation.screens.DetailsScreen

class DetailsScreenimpl : DetailsScreen {
    override var destinationFragment: Fragment? = null

    override val tag = DetailFragment.TAG

    override fun destination(args: Bundle) {
        destinationFragment = DetailFragment.newInstance(args)
    }
}