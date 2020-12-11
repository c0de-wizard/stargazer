package com.thomaskioko.githubstargazer.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.thomaskioko.githubstargazer.R
import com.thomaskioko.githubstargazer.databinding.ActivityMainBinding
import com.thomaskioko.githubstargazer.injection.MainActivityComponent
import com.thomaskioko.githubstargazer.injection.injectAndGetComponent
import com.thomaskioko.githubstargazer.ui.navigation.ActivityScreenNavigator
import com.thomaskioko.stargazer.navigation.NAVIGATION_DEPS_SERVICE
import com.thomaskioko.stargazer.navigation.NavigationScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListScreen
import com.thomaskioko.stargazer.navigation.Navigator
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ScreenNavigator {

    @Inject
    lateinit var activityScreenNavigator: ActivityScreenNavigator

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private lateinit var component: MainActivityComponent

    private lateinit var binding: ActivityMainBinding

    private val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        component = injectAndGetComponent()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment)

        navigator.navController = navController

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bookmarks -> screenNavigator.goToScreen(BookmarkListScreen)
        }

        return super.onOptionsItemSelected(item)
    }


    override fun getSystemService(name: String): Any? {
        if (name == NAVIGATION_DEPS_SERVICE) {
            return component
        }
        return super.getSystemService(name)
    }

    override fun goToScreen(navigationScreen: NavigationScreen) {
        navigator.navigateToScreen(navigationScreen)
    }
}
