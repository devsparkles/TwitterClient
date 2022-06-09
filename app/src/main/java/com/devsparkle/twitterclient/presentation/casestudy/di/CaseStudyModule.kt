package com.devsparkle.twitterclient.presentation.casestudy.di

import com.devsparkle.twitterclient.domain.model.CaseStudy
import com.devsparkle.twitterclient.domain.use_case.GetCaseStudies
import com.devsparkle.twitterclient.domain.use_case.PersistCaseStudies
import com.devsparkle.twitterclient.presentation.casestudy.adapter.CaseStudyAdapter
import com.devsparkle.twitterclient.presentation.casestudy.viewmodel.ListCaseStudyViewModel
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val caseStudyModule = module {

    viewModel {
        ListCaseStudyViewModel(
            get<PersistCaseStudies>(),
            get<GetCaseStudies>(),
            get<CoroutineDispatcher>()
        )
    }

    factory { (clickCallback: ((CaseStudy) -> Unit)) ->
        CaseStudyAdapter(
            clickCallback
        )
    }

}
