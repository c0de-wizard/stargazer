package com.thomaskioko.stargazers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.thomaskioko.stargazer.common_ui.R

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setBinding(inflater, container)
        setupMenuButtons()
        return binding.root
    }

    private fun setupMenuButtons() {
        val toolbar: Toolbar? = getToolbar()
        toolbar?.let {
            it.inflateMenu(R.menu.menu_settings)
            it.setOnMenuItemClickListener { item ->
                when (item?.itemId) {
                    R.id.item_settings -> {
                        navigateToSettingsScreen()
                        true
                    }
                    else -> false
                }
            }
        }

    }

    protected abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): T

    protected abstract fun getToolbar(): Toolbar?

    protected abstract fun navigateToSettingsScreen()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
