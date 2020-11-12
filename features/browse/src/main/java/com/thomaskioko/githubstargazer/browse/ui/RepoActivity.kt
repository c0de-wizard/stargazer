package com.thomaskioko.githubstargazer.browse.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.thomaskioko.githubstargazer.browse.R
import com.thomaskioko.githubstargazer.browse.databinding.ActivityRepoBinding
import com.thomaskioko.stargazer.actions.NavigationRoute

class RepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bookmarks -> startActivity(NavigationRoute.openBookmarkedActivity(this))
        }

        return super.onOptionsItemSelected(item)
    }
}
