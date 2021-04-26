package com.thomaskioko.githubstargazer.mapper

import com.thomaskioko.githubstargazer.mock.MockData.makeRepoResponseList
import com.thomaskioko.stargazer.mapper.RepositoryMapper.mapResponseToEntityList
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class RepositoryMapperTest {

    @Test
    fun givenResponseList_thenDataIsMappedToEntityList() {

        val reposResponse = makeRepoResponseList()
        val entityList = mapResponseToEntityList(reposResponse, true)

        val response = reposResponse[0]
        val entity = entityList[0]

        assertThat(reposResponse.size).isEqualTo(entityList.size)
        assertThat(response.id).isEqualTo(entity.id)
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
