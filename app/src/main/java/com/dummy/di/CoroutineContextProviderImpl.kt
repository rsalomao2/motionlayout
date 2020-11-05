package com.dummy.di

import com.dummy.domain.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class CoroutineContextProviderImpl : CoroutineContextProvider {
    override val MAIN: CoroutineContext by lazy { Dispatchers.Main }
    override val IO: CoroutineContext by lazy { Dispatchers.IO }
    override val DEFAULT: CoroutineContext by lazy { Dispatchers.Default }
}
