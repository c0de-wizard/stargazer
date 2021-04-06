package com.thomaskioko.stargazer.trending.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import com.thomaskioko.stargazer.trending.model.RepoViewDataModel
import com.thomaskioko.stargazer.trending.ui.mockdata.RepoRepository.getRepository
import com.thomaskioko.stargazers.common.compose.R
import com.thomaskioko.stargazers.common.compose.components.OutlinedAvatar
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.common.compose.theme.black
import com.thomaskioko.stargazers.common.compose.theme.favorite
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun RepoCardItem(
    repo: RepoViewDataModel,
    onRepoItemClicked: (Long) -> Unit = { }
) {

    Column(
        modifier = Modifier
            .clickable(onClick = { onRepoItemClicked(repo.repoId) })
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        RepositoryUserInfo(repo = repo)
        RepositoryDetails(repo = repo)
        RepoMetaData(repo = repo)
    }

}

@Composable
fun RepositoryUserInfo(repo: RepoViewDataModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        OutlinedAvatar(
            url = repo.avatarUrl,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = repo.userName,
            maxLines = 1,
            style = MaterialTheme.typography.caption,
        )
    }

    Spacer(Modifier.height(8.dp))
}

@Composable
fun RepositoryDetails(repo: RepoViewDataModel) {
    Column {
        Text(
            text = repo.repoName,
            maxLines = 1,
            style = MaterialTheme.typography.h6,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = repo.description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

    }
}

@Composable
fun RepoListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
fun RepoMetaData(
    repo: RepoViewDataModel,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            RepoLanguageMetaData(
                text = repo.language,
                drawableId = repo.languageDrawable,
                iconColor = repo.drawableColor
            )

            val tintColor = if (repo.isBookmarked) favorite else black
            RepoCountMetaData(
                text = repo.stargazersCount.toString(),
                drawableId = R.drawable.ic_favorite_black,
                iconColor = tintColor,
                contentDescription = "Repository star count",
            )

            RepoCountMetaData(
                text = repo.forksCount.toString(),
                drawableId = R.drawable.ic_fork,
                contentDescription = "Repository fork count",
            )
        }
    }
}

@Composable
fun RepoLanguageMetaData(
    modifier: Modifier = Modifier,
    text: String,
    drawableId: Int,
    contentDescription: String? = null,
    iconColor: Color = black
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Icon(
            painter = painterResource(id = drawableId),
            tint = iconColor,
            contentDescription = contentDescription,
            modifier = Modifier.size(18.dp, 18.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 4.dp, end = 4.dp),
            )
        }
    }
}

@Composable
fun RepoCountMetaData(
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
                    .align(Alignment.CenterVertically)
                    .padding(start = 4.dp, end = 4.dp),
            )
        }

    }
}


@Preview(
    name = "Repository Item",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Repository Item • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun RepoListPreview() {
    val repoList = remember { getRepository() }
    StargazerTheme {
        Surface {
            RepoCardItem(repo = repoList)
        }
    }
}
