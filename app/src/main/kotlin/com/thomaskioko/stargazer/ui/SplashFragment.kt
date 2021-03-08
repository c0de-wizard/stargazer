package com.thomaskioko.stargazer.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thomaskioko.stargazer.R
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoListScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val SPLASH_DELAY_DURATION: Long = 1500

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper())
            .postDelayed(
                {
                    screenNavigator.goToScreen(RepoListScreen)
                },
                SPLASH_DELAY_DURATION
            )
    }
}
