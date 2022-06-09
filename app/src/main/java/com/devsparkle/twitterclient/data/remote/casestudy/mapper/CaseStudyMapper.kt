package com.devsparkle.twitterclient.data.remote.casestudy.mapper

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.local.casestudy.entities.CaseStudyEntity
import com.devsparkle.twitterclient.data.remote.casestudy.dto.CaseStudyDto
import com.devsparkle.twitterclient.data.remote.casestudy.dto.CaseStudyWrapperDto
import com.devsparkle.twitterclient.domain.model.CaseStudy
import com.devsparkle.twitterclient.domain.model.CaseStudyWrapper

fun Resource<CaseStudyWrapperDto?>.toDomain(): Resource<CaseStudyWrapper> {
    return when (this) {
        is Resource.Success -> Resource.Success(this.value().toDomain())
        is Resource.SuccessWithoutContent -> Resource.SuccessWithoutContent()
        is Resource.Error -> Resource.Error(this.error())
        is Resource.Loading -> Resource.Loading()
    }
}

fun List<CaseStudyDto>.toDomain(): MutableList<CaseStudy> {
    val result: MutableList<CaseStudy> = mutableListOf()
    this.forEach { result.add(it.toDomain()) }
    return result
}


fun CaseStudyWrapperDto?.toDomain(): CaseStudyWrapper {
    return CaseStudyWrapper(
        this?.case_studies?.toDomain(),
    )
}


fun CaseStudyDto?.toDomain(): CaseStudy {
    return CaseStudy(
        this?.teaser,
        this?.hero_image
    )
}


fun CaseStudy.toEntity(): CaseStudyEntity {
    return CaseStudyEntity(
        teaser = this.teaser ?: "",
        coverimageUrl =  this.heroImageUrl ?: ""
    )
}


fun CaseStudyEntity?.toDomain(): CaseStudy {
    return CaseStudy(
        this?.teaser,
        this?.coverimageUrl
    )
}

fun List<CaseStudyEntity>.toDomainCaseStudy(): List<CaseStudy> {
    val result: MutableList<CaseStudy> = mutableListOf()
    this.forEach { result.add(it.toDomain()) }
    return result.toList()
}

