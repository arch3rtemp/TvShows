package dev.arch3rtemp.tvshows.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.feature.tvshow.ui.home.HomeFragment
import dev.arch3rtemp.tvshows.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var container: FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.fragmentContainer)
        val homeFragment = HomeFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, homeFragment)
            .commit()
    }
}