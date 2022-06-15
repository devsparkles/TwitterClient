package com.devsparkle.twitterclient.base.resource


sealed class Resource<T> {
    abstract fun value(): T?
    abstract fun error(): Exception?

    fun isAnError() = error() != null
    fun isNotAnError() = error() == null

    data class Success<T>(private val value: T) : Resource<T>() {
        override fun value() = value
        override fun error() = null
    }

    class SuccessWithoutContent<T> : Resource<T>() {
        override fun value() = null
        override fun error() = null
    }

    class Loading<T> : Resource<T>() {
        override fun value() = null
        override fun error() = null
    }

    open class Error<T>(private var exception: Exception = Exception()) : Resource<T>() {
        override fun value() = null
        override fun error() = exception
    }


    companion object {
        suspend fun <V> of(suspendFunction: suspend () -> V): Resource<V> = try {
            val value = suspendFunction()
            Success(value)
        } catch (exception: Exception) {
            Error(exception)
        }
    }
}
