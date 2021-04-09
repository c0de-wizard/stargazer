package com.thomaskioko.stargazer.trending.model

import com.google.common.truth.Truth
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.trending.model.ViewDataMapper.mapEntityToViewModel
import org.junit.jupiter.api.Test


internal class ViewDataMapperTest{

    @Test
    fun `givenEntity dataIsMappedTo ViewModelData`() {

        val entity = makeRepoEntityList()[0]
        val viewDataModel =  mapEntityToViewModel(entity)

        Truth.assertThat(entity.repoId).isEqualTo(viewDataModel.repoId)
        Truth.assertThat(entity.name).isEqualTo(viewDataModel.repoName)
        Truth.assertThat(entity.userName).isEqualTo(viewDataModel.userName)
        Truth.assertThat(entity.description).isEqualTo(viewDataModel.description)
        Truth.assertThat(entity.stargazersCount).isEqualTo(viewDataModel.stargazersCount)
        Truth.assertThat(entity.forksCount).isEqualTo(viewDataModel.forksCount)
        Truth.assertThat(entity.contributorsUrl).isEqualTo(viewDataModel.contributorsUrl)
        Truth.assertThat(entity.createdDate).isEqualTo(viewDataModel.createdDate)
        Truth.assertThat(entity.updatedDate).isEqualTo(viewDataModel.updatedDate)
        Truth.assertThat(entity.isBookmarked).isEqualTo(viewDataModel.isBookmarked)
    }
}