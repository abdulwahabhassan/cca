package com.smartflowtech.cupidcustomerapp.model.result


sealed class RepositoryResult<out T>() {
    data class Success<out T>(val data: T, val message: String? = null) : RepositoryResult<T>()
    data class Error(val message: String?) : RepositoryResult<Nothing>()
}

