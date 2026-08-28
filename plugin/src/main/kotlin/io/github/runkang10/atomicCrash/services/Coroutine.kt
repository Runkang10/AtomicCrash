package io.github.runkang10.atomicCrash.utilities

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object Coroutine {
    private val coroutine = CoroutineScope(Dispatchers.IO + SupervisorJob())


    fun launch(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ) = coroutine.launch(context) { block() }

    fun cancel() = coroutine.cancel()
}