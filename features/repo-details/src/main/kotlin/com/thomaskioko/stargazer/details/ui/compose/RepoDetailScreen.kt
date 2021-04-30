package com.thomaskioko.stargazer.details.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.accompanist.insets.statusBarsPadding
import com.thomaskioko.stargazer.details.R
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazer.details.ui.DetailViewState
import com.thomaskioko.stargazer.details.ui.compose.mockdata.repoViewDataModel
import com.thomaskioko.stargazer.details.ui.viewmodel.RepoDetailsViewModel
import com.thomaskioko.stargazers.common.compose.components.AppBarPainterIcon
import com.thomaskioko.stargazers.common.compose.components.CircularLoadingView
import com.thomaskioko.stargazers.common.compose.components.OutlinedAvatar
import com.thomaskioko.stargazers.common.compose.components.SnackBarErrorRetry
import com.thomaskioko.stargazers.common.compose.components.StargazerFab
import com.thomaskioko.stargazers.common.compose.components.StargazersScaffold
import com.thomaskioko.stargazers.common.compose.components.StargazersTopBar
import com.thomaskioko.stargazers.common.compose.components.util.NetworkImage
import com.thomaskioko.stargazers.common.compose.components.util.scrim
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun RepoDetailScreen(
    viewModel: RepoDetailsViewModel,
    onBackPressed: () -> Unit = { },
    onRepoBookMarked: (RepoViewDataModel) -> Unit = { },
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current
    val actionState = viewModel.stateFlow

    val actionStateLifeCycleAware = remember(actionState, lifecycleOwner) {
        actionState.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val viewState by actionStateLifeCycleAware
        .collectAsState(initial = DetailViewState.Loading)

    StargazersScaffold(
        scaffoldState = scaffoldState,
        content = {
            RepoDetailContent(
                viewState,
                scaffoldState,
                coroutineScope,
                onRepoBookMarked,
                onBackPressed
            )
        }
    )
}

@Composable
private fun RepoDetailContent(
    viewState: DetailViewState,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    onRepoBookMarked: (RepoViewDataModel) -> Unit,
    onBackPressed: () -> Unit = { },
) {
    when (viewState) {
        DetailViewState.Loading -> CircularLoadingView()
        is DetailViewState.Error -> SnackBarErrorRetry(
            snackBarHostState = scaffoldState.snackbarHostState,
            coroutineScope = coroutineScope,
            errorMessage = viewState.message,
            onErrorAction = {}
        )
        is DetailViewState.Success -> RepoDetails(
            onRepoBookMarked = onRepoBookMarked,
            repo = viewState.viewDataModel,
            onBackPressed = onBackPressed
        )
    }
}

@Composable
internal fun RepoDetails(
    repo: RepoViewDataModel,
    onRepoBookMarked: (RepoViewDataModel) -> Unit = { },
    onBackPressed: () -> Unit = { },
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
                    RepoDetailsHeader(repo, onBackPressed)
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
internal fun RepoDetailsHeader(
    repo: RepoViewDataModel,
    onBackPressed: () -> Unit = { },
) {

    Box {
        NetworkImage(
            url = repo.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .scrim(colors = listOf(Color(0x80000000), Color(0x33000000)))
                .aspectRatio(4f / 3f)
        )

        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentColor = Color.White, // always white as image has dark scrim
            modifier = Modifier.statusBarsPadding()
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

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

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = repo.repoName,
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "Language",
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
        )

        Text(
            text = repo.language,
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 8.dp, top = 16.dp)
        )
    }

    UserMetadata(repo)

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = repo.description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun UserMetadata(repo: RepoViewDataModel) {
    val typography = MaterialTheme.typography
    Row(
        // Merge semantics so accessibility services consider this row a single element
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .semantics(mergeDescendants = true) {}
    ) {
        Image(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.width(8.dp))

        Column {
            Text(
                text = repo.userName,
                style = typography.caption,
                modifier = Modifier.padding(top = 4.dp)
            )

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = repo.userType,
                    style = typography.caption
                )
            }

        }
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
