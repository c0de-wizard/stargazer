package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import javax.inject.Inject

class GetReposViewModel @Inject constructor(
    repository: GithubRepository
) : ViewModel() {
}