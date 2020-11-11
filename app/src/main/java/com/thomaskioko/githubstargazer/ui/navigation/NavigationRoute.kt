package com.thomaskioko.githubstargazer.ui.navigation

import android.content.Context
import android.content.Intent

object NavigationRoute {

    fun openRepositoryActivity(context: Context) = internalIntent(context, "com.thomaskioko.githubstargazer.browse.ui.open")

    private fun internalIntent(context: Context, action: String) = Intent(action).setPackage(context.packageName)
}
