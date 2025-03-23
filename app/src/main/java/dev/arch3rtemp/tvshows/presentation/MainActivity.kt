package dev.arch3rtemp.tvshows.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
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
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val container = findViewById<FragmentContainerView>(R.id.fragmentContainer)

        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = false

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