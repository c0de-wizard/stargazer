package com.thomaskioko.stargazer.ui

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
import com.thomaskioko.stargazer.R
import com.thomaskioko.stargazer.databinding.ActivityMainBinding
import com.thomaskioko.stargazer.domain.SettingsManager
import com.thomaskioko.stargazer.domain.UiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@InternalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navControllerProvider: Provider<NavController>

    @Inject
    lateinit var settingsManager: SettingsManager

    private lateinit var binding: ActivityMainBinding
    private var isDarkMode = false

    private val navController: NavController
        get() = navControllerProvider.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
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

    private fun setTheme() {
        GlobalScope.launch(context = Dispatchers.Main) {
            settingsManager.getUiModeFlow()
                .collect {
                    isDarkMode = when (it) {
                        UiTheme.LIGHT -> {
                            setDefaultNightMode(MODE_NIGHT_NO)
                            false
                        }
                        UiTheme.DARK -> {
                            setDefaultNightMode(MODE_NIGHT_YES)
                            true
                        }
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val menuItem = menu.findItem(R.id.menu_item_theme)
        val switch = (menuItem.actionView as SwitchMaterial)

        switch.isChecked = isDarkMode

        switch.setOnCheckedChangeListener { _, isChecked ->
            isDarkMode = if (isChecked) {
                updateTheme(UiTheme.DARK)
                true
            } else {
                updateTheme(UiTheme.LIGHT)
                false
            }
        }
        return true
    }

    private fun updateTheme(themeTheme: UiTheme) {
        GlobalScope.launch(context = Dispatchers.Main) {
            settingsManager.setUiMode(themeTheme)
        }
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
