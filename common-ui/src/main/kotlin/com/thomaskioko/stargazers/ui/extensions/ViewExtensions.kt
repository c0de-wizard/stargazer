package com.thomaskioko.stargazers.ui.extensions

import android.view.View
import com.google.android.material.card.MaterialCardView

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun MaterialCardView.setAllCornerSizes(interpolation: Float) {
    shapeAppearanceModel = shapeAppearanceModel.toBuilder()
        .setAllCornerSizes(interpolation)
        .build()
}
