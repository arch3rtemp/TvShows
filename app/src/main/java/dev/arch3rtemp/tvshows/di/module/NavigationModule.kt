package dev.arch3rtemp.tvshows.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import dev.arch3rtemp.navigation.Navigator
import dev.arch3rtemp.navigation.NavigatorImpl
import dev.arch3rtemp.tvshows.R
import dev.arch3rtemp.tvshows.presentation.MainActivity

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {

    @Provides
    @ActivityScoped
    fun bindNavigation(@ActivityContext context: Context): Navigator {
        val fragmentManager = (context as MainActivity).supportFragmentManager
        return NavigatorImpl(fragmentManager, R.id.fragmentContainer)
    }
}