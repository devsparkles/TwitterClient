package com.devsparkle.twitterclient.base.resource

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<Resource<T>>.observeResource(owner: LifecycleOwner, success: (T?) -> Unit) {
    val observer = ResourceObserver<T>(success = success)
    observe(owner, observer)
}

fun <T> LiveData<Resource<T>>.observeResource(
    owner: LifecycleOwner,
    loading: (() -> Unit),
    error: ((Exception) -> Unit),
    success: ((T?) -> Unit),
    successWithoutContent: (() -> Unit)? = null
) {
    val observer = ResourceObserver<T>(
        loading = loading,
        success = success,
        successWithoutContent = successWithoutContent,
        error = error
    )
    observe(owner, observer)
}


fun <E> List<E>.getCorrectSuccessResourceType(): Resource<List<E>> {
    return if (this.isEmpty()) Resource.SuccessWithoutContent()
    else Resource.Success(this)
}
