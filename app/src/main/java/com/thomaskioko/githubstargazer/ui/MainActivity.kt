package com.thomaskioko.githubstargazer.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.thomaskioko.githubstargazer.R
import com.thomaskioko.githubstargazer.databinding.ActivityMainBinding
import com.thomaskioko.stargazer.navigation.NavigationScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListScreen
import com.thomaskioko.stargazer.navigation.Navigator
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ScreenNavigator {

    private lateinit var binding: ActivityMainBinding

    //TODO:: Inject ScreenNavigator
    private val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment)

        navigator.navController = navController

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bookmarks -> goToScreen(BookmarkListScreen)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun goToScreen(navigationScreen: NavigationScreen) {
        navigator.navigateToScreen(navigationScreen)
    }
}
