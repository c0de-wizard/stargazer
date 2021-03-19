package com.thomaskioko.stargazers.common.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomaskioko.stargazers.common.compose.R
import com.thomaskioko.stargazers.common.compose.mockdata.Repo
import com.thomaskioko.stargazers.common.compose.mockdata.RepoRepository
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.common.compose.theme.black
import com.thomaskioko.stargazers.common.compose.theme.favorite
import dev.chrisbanes.accompanist.coil.CoilImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepositoryCardItem(
    repo: Repo,
    modifier: Modifier = Modifier
) {
    /** Handle click action **/
    /** Handle click action **/
    Card(modifier) {
        /** Handle click action **/
        /** Handle click action **/
        ListItem(
            modifier = modifier
                .clickable { /** Handle click action **/ }
                .padding(vertical = 8.dp),
            icon = {
                CoilImage(
                    data = repo.avatarUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.small),
                    loading = {
                        Box(Modifier.matchParentSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    },
                    error = {
                        Icon(
                            painter = painterResource(R.drawable.octocat),
                            contentDescription = null,
                        )
                    })
            },
            text = {
                Text(
                    text = repo.name,
                    maxLines = 1,
                    style = MaterialTheme.typography.h6,
                )
            },
            secondaryText = {
                RepoMetaData(repo = repo)
            }
        )
    }
}

@Composable
fun RepoMetaData(
    repo: Repo,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Text(
            text = repo.description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row {
            val tintColor = if (repo.isBookmarked) favorite else black
            RepoRowMetaData(
                text = repo.stargazersCount.toString(),
                drawableId = R.drawable.ic_favorite_black,
                iconColor = tintColor,
                contentDescription = "Repository star count",
            )

            RepoRowMetaData(
                text = repo.forksCount.toString(),
                drawableId = R.drawable.ic_fork,
                contentDescription = "Repository fork count",
            )
        }
    }
}

@Composable
fun RepoRowMetaData(
    modifier: Modifier = Modifier,
    text: String,
    drawableId: Int,
    contentDescription: String? = null,
    iconColor: Color = black
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = drawableId),
            contentDescription = contentDescription,
            tint = iconColor,
        )

        Spacer(modifier = Modifier.width(4.dp))

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
                    .padding(start = 4.dp, end = 4.dp),
            )
        }

    }
}




@Preview("Repository Item")
@Composable
private fun RepoItemPreview() {
    val repo = remember { RepoRepository.getRepository() }
    StargazerTheme {
        RepositoryCardItem(repo = repo)
    }
}

@Preview("Repository Item®")
@Composable
private fun RepoItemDarkPreview() {
    val repo = remember { RepoRepository.getRepository() }
    StargazerTheme(darkTheme = true) {
        RepositoryCardItem(repo = repo)
    }
}
