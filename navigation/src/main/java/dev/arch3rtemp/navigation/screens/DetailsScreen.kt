package dev.arch3rtemp.navigation.screens

import android.os.Bundle
import dev.arch3rtemp.navigation.Screen

interface DetailsScreen : Screen {
    fun destination(args: Bundle)
}