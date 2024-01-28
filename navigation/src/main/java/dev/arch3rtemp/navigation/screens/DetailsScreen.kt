package dev.arch3rtemp.navigation.screens

import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import dev.arch3rtemp.navigation.Screen

interface DetailsScreen : Screen {
    fun destination(tvShow: TvShowUi)
}