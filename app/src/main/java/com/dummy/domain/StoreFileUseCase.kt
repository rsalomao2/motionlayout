package com.dummy.domain

import okhttp3.ResponseBody

interface StoreFileUseCase {
    suspend fun store(responseBody: ResponseBody, locationPath: String, fileName: String): Status<String>
}