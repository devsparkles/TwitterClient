package com.devsparkle.twitterclient.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val isNetworkAvailable = MutableLiveData<Boolean>()

}