package com.thomaskioko.stargazer.bookmarks.model.mapper

import com.google.common.truth.Truth.assertThat
import com.thomaskioko.stargazer.bookmarks.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.bookmarks.model.mapper.ViewDataMapper.mapEntityToRepoViewModel
import org.junit.jupiter.api.Test


internal class ViewDataMapperTest {

    @Test
    fun `givenEntityList dataIsMappedTo ViewModelDataList`() {

        val entityList = makeRepoEntityList()
        val viewModelList = entityList.map { mapEntityToRepoViewModel(it) }

        val entity = entityList[0]
        val viewDataModel = viewModelList[0]

        assertThat(entityList.size).isEqualTo(viewModelList.size)

        assertThat(entity.repoId).isEqualTo(viewDataModel.repoId)
        assertThat(entity.name).isEqualTo(viewDataModel.repoName)
        assertThat(entity.userName).isEqualTo(viewDataModel.userName)
        assertThat(entity.description).isEqualTo(viewDataModel.description)
        assertThat(entity.stargazersCount).isEqualTo(viewDataModel.stargazersCount)
        assertThat(entity.forksCount).isEqualTo(viewDataModel.forksCount)
        assertThat(entity.contributorsUrl).isEqualTo(viewDataModel.contributorsUrl)
        assertThat(entity.createdDate).isEqualTo(viewDataModel.createdDate)
        assertThat(entity.updatedDate).isEqualTo(viewDataModel.updatedDate)
        assertThat(entity.isBookmarked).isEqualTo(viewDataModel.isBookmarked)
    }
}
