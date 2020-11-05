package com.dummy.domain

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val MAIN: CoroutineContext
    val IO: CoroutineContext
    val DEFAULT: CoroutineContext
}
