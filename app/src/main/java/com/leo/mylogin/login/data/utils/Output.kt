package com.leo.mylogin.login.data.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

sealed class Output<out R> {
    data class Success<out R>(val result: R): Output<R>()
    data class Failure(val e: Exception): Output<Nothing>()
    object Loading: Output<Nothing>()
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.parseResponse(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }
        }
    }
}