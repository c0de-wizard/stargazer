package com.thomaskioko.githubstargazer.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.thomaskioko.githubstargazer.R
import com.thomaskioko.githubstargazer.databinding.ActivityMainBinding
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navControllerProvider: Provider<NavController>

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private lateinit var binding: ActivityMainBinding

    private val navController: NavController
        get() = navControllerProvider.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.bottomNavigation.setupWithNavController(navController)

        // Hide bottom nav on screens which don't require it
        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.repoListFragment, R.id.mviRepoListFragment, R.id.bookmarkListFragment ->
                        binding.bottomNavigation.visibility =
                            View.VISIBLE
                    else -> binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val menuItem = menu.findItem(R.id.menu_item_theme)
        val switch = (menuItem.actionView as SwitchMaterial)

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked ) {
                setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                setDefaultNightMode(MODE_NIGHT_NO)
            }

        }
        return true
    }

    private fun isDarkModeOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }


    override fun onBackPressed() {
        if (!onSupportNavigateUp()) {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
