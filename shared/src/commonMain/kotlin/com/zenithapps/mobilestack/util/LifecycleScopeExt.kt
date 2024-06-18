package com.zenithapps.mobilestack.util

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private fun CoroutineScope.cancelInLifecycle(lifecycle: Lifecycle): CoroutineScope {
    if (lifecycle.state == Lifecycle.State.DESTROYED)
        cancel()
    else
        lifecycle.doOnDestroy(this::cancel)
    return this
}

fun LifecycleOwner.createCoroutineScope(context: CoroutineContext = Dispatchers.Main + SupervisorJob()): CoroutineScope {
    return CoroutineScope(context).cancelInLifecycle(lifecycle)
}

fun <T> Flow<T>.collectIn(scope: CoroutineScope, collector: FlowCollector<T>): Job =
    scope.launch {
        collect(collector)
    }