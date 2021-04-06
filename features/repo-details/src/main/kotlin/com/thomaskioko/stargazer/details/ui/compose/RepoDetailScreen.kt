package com.thomaskioko.stargazer.details.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.thomaskioko.stargazer.details.R
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazer.details.ui.DetailViewState
import com.thomaskioko.stargazer.details.ui.compose.mockdata.repoViewDataModel
import com.thomaskioko.stargazer.details.ui.viewmodel.RepoDetailsViewModel
import com.thomaskioko.stargazers.common.compose.components.AppBarBackIcon
import com.thomaskioko.stargazers.common.compose.components.CircularLoadingView
import com.thomaskioko.stargazers.common.compose.components.OutlinedAvatar
import com.thomaskioko.stargazers.common.compose.components.SnackBarErrorRetry
import com.thomaskioko.stargazers.common.compose.components.StargazerFab
import com.thomaskioko.stargazers.common.compose.components.StargazersTopBar
import com.thomaskioko.stargazers.common.compose.components.util.NetworkImage
import com.thomaskioko.stargazers.common.compose.components.util.scrim
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme

@Composable
internal fun RepoDetailScreen(
    viewModel: RepoDetailsViewModel,
    onBackPressed: () -> Unit = { },
    onRepoBookMarked: (RepoViewDataModel) -> Unit = { },
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val actionState = viewModel.stateFlow

    val actionStateLifeCycleAware = remember (actionState, lifecycleOwner){
        actionState.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val viewState by actionStateLifeCycleAware
        .collectAsState(initial = DetailViewState.Loading)

    Scaffold(
        topBar = {
            StargazersTopBar(
                title = { },
                navigationIcon = { AppBarBackIcon(onBackPressed = onBackPressed) }
            )
        },
        content = {
            when (viewState) {
                DetailViewState.Loading -> CircularLoadingView()
                is DetailViewState.Error -> SnackBarErrorRetry(
                    errorMessage = (viewState as DetailViewState.Error).message,
                    onErrorAction = {}
                )
                is DetailViewState.Success -> RepoDetails(
                    onRepoBookMarked = onRepoBookMarked,
                    repo = (viewState as DetailViewState.Success).viewDataModel,
                )
            }
        }
    )
}

@Composable
internal fun RepoDetails(
    repo: RepoViewDataModel,
    onRepoBookMarked: (RepoViewDataModel) -> Unit = { },
) {
    val scrollState = rememberScrollState()
    val text = if (repo.isBookmarked) stringResource(id = R.string.fab_remove_bookmark)
    else stringResource(id = R.string.fab_bookmark)

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f),
        ) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    RepoDetailsHeader(repo)
                    RepoDetailsBody(repo)
                }
            }
            StargazerFab(
                extended = scrollState.value == 0,
                isBookmarked = repo.isBookmarked,
                fabText = text,
                modifier = Modifier.align(Alignment.BottomEnd),
                onRepoBookMarked = {
                    onRepoBookMarked(
                        repo.copy(isBookmarked = !repo.isBookmarked)
                    )
                }
            )
        }
    }
}

@Composable
internal fun RepoDetailsHeader(repo: RepoViewDataModel) {

    Box {
        NetworkImage(
            url = "",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .scrim(colors = listOf(Color(0x80000000), Color(0x33000000)))
                .aspectRatio(4f / 3f)
        )

        OutlinedAvatar(
            url = repo.avatarUrl,
            outlineSize = 3.dp,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(40.dp)
                .align(Alignment.BottomEnd)
                .offset(y = 20.dp) // overlap bottom of image

        )
    }

}

@Composable
internal fun RepoDetailsBody(repo: RepoViewDataModel) {

    Text(
        text = repo.language,
        color = MaterialTheme.colors.secondary,
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 36.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    )
    Text(
        text = repo.repoName,
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = repo.description,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview(
    name = "Repo Details",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4_XL
)
@Preview(
    name = "Repo Details • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4_XL
)
@Composable
fun RepoDetailsPreview() {
    StargazerTheme {
        RepoDetails(repo = repoViewDataModel())
    }
}

