package com.thomaskioko.githubstargazer.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thomaskioko.githubstargazer.R
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoListScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator


class SplashFragment : Fragment() {

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
                    (requireActivity() as ScreenNavigator).goToScreen(RepoListScreen)
                },
                1500
            )
    }

}