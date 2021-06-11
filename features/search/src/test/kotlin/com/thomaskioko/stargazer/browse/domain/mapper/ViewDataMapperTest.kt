package com.thomaskioko.stargazer.browse.domain.mapper

import com.google.common.truth.Truth.assertThat
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.browse.model.ViewDataMapper.mapEntityToRepoViewModel
import org.junit.Test

internal class ViewDataMapperTest {

    @Test
    fun `givenEntityList dataIsMappedTo ViewModelDataList`() {

        val entityList = makeRepoEntityList()
        val viewModelList = entityList.map { mapEntityToRepoViewModel(it) }

        val entity = entityList[0]
        val viewDataModel = viewModelList[0]

        assertThat(entityList.size).isEqualTo(viewModelList.size)

        assertThat(viewDataModel.repoId).isEqualTo(entity.id)
        assertThat(viewDataModel.repoName).isEqualTo(entity.name)
        assertThat(viewDataModel.userName).isEqualTo(entity.userName)
        assertThat(viewDataModel.description).isEqualTo(entity.description)
        assertThat(viewDataModel.stargazersCount).isEqualTo("134")
        assertThat(viewDataModel.forksCount).isEqualTo("1.2k")
        assertThat(viewDataModel.contributorsUrl).isEqualTo(entity.contributorsUrl)
        assertThat(viewDataModel.createdDate).isEqualTo(entity.createdDate)
        assertThat(viewDataModel.updatedDate).isEqualTo(entity.updatedDate)
        assertThat(viewDataModel.isBookmarked).isEqualTo(entity.isBookmarked)
    }
}
