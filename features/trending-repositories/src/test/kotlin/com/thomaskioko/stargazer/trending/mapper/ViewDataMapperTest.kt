package com.thomaskioko.stargazer.trending.mapper

import com.google.common.truth.Truth.assertThat
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.trending.mapper.ViewDataMapper.mapEntityToViewModel
import org.junit.jupiter.api.Test


internal class ViewDataMapperTest{

    @Test
    fun `givenEntity dataIsMappedTo ViewModelData`() {

        val entity = makeRepoEntityList()[0]
        val viewDataModel =  mapEntityToViewModel(entity)

        assertThat(viewDataModel.repoId).isEqualTo(entity.id)
        assertThat(viewDataModel.repoName).isEqualTo(entity.name)
        assertThat(viewDataModel.userName).isEqualTo(entity.userName)
        assertThat(viewDataModel.description).isEqualTo(entity.description)
        assertThat(viewDataModel.stargazersCount).isEqualTo("1.2k")
        assertThat(viewDataModel.forksCount).isEqualTo("1.2k")
        assertThat(viewDataModel.contributorsUrl).isEqualTo(entity.contributorsUrl)
        assertThat(viewDataModel.createdDate).isEqualTo(entity.createdDate)
        assertThat(viewDataModel.updatedDate).isEqualTo(entity.updatedDate)
        assertThat(viewDataModel.isBookmarked).isEqualTo(entity.isBookmarked)
    }
}
