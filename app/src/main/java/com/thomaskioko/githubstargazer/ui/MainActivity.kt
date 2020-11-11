package com.thomaskioko.githubstargazer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thomaskioko.githubstargazer.R
import com.thomaskioko.githubstargazer.ui.navigation.NavigationRoute

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(NavigationRoute.openRepositoryActivity(this))
    }
}
