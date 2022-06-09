package com.devsparkle.twitterclient.presentation.casestudy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.CaseStudy
import com.devsparkle.twitterclient.domain.use_case.GetCaseStudies
import com.devsparkle.twitterclient.domain.use_case.PersistCaseStudies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListCaseStudyViewModel(
    private val persistCaseStudies: PersistCaseStudies,
    private val getCaseStudies: GetCaseStudies,
    private val coroutineContext: CoroutineDispatcher
) : ViewModel() {

    private val _caseStudies = MutableLiveData<Resource<List<CaseStudy>?>>()
    val caseStudies: LiveData<Resource<List<CaseStudy>?>> = _caseStudies

    fun getCaseStudies(isConnected: Boolean) {
        viewModelScope.launch(coroutineContext) {
            _caseStudies.postValue(Resource.Loading())
            withContext(Dispatchers.IO) {
                val response = getCaseStudies.invoke(isConnected)
                if (response.isNotAnError()) {
                    _caseStudies.postValue(response)
                } else {
                    _caseStudies.postValue(Resource.Error())
                }
            }
        }
    }


    fun persistCaseStudies(list: List<CaseStudy>) {
        viewModelScope.launch(coroutineContext) {
            withContext(Dispatchers.IO) {
                persistCaseStudies.invoke(list)
            }
        }
    }
}