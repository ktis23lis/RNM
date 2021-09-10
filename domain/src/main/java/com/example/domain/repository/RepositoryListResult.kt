package com.example.domain.repository

sealed class RepositoryListResult<T>
data class SuccessList<T>(val value: T): RepositoryListResult<T>()
data class ErrorList<T>(val value: Throwable): RepositoryListResult<T>()