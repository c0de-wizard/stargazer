package com.thomaskioko.githubstargazer.core.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("count")
fun TextView.setCount(count: Int) {
    text = "$count"
}