package com.thomaskioko.githubstargazer.repository.mapper

import com.google.common.truth.Truth.*
import com.thomaskioko.githubstargazer.repository.mapper.RepositoryMapper.mapResponseToEntityList
import com.thomaskioko.githubstargazer.repository.util.MockData.makeRepoResponseList
import org.junit.Test

internal class RepositoryMapperTest {

    @Test
    fun `givenResponseList dataIsMappedTo EntityList`() {

        val reposResponse = makeRepoResponseList()
        val entityList = mapResponseToEntityList(reposResponse)

        val response = reposResponse[0]
        val entity = entityList[0]

        assertThat(reposResponse.size).isEqualTo(entityList.size)
        assertThat(response.id).isEqualTo(entity.repoId)
        assertThat(response.name).isEqualTo(entity.name)
        assertThat(response.owner.login).isEqualTo(entity.userName)
        assertThat(response.description).isEqualTo(entity.description)
        assertThat(response.stargazersCount).isEqualTo(entity.stargazersCount)
        assertThat(response.forksCount).isEqualTo(entity.forksCount)
        assertThat(response.contributorsUrl).isEqualTo(entity.contributorsUrl)
        assertThat(response.createdDate).isEqualTo(entity.createdDate)
        assertThat(response.updatedDate).isEqualTo(entity.updatedDate)
    }
}
