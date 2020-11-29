package com.thomaskioko.githubstargazer.core.extensions

import android.view.View

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}