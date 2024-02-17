package dev.arch3rtemp.tvshows.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.feature.tvshow.ui.navigation.HomeScreenImpl
import dev.arch3rtemp.navigation.Navigator
import dev.arch3rtemp.tvshows.R
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeScreen = HomeScreenImpl()
        homeScreen.destination()
        navigator.navigateWithReplaceTo(
            homeScreen,
            allowingStateLoss = false,
            addToBackStack = false,
            allowReordering = true
        )
    }
}