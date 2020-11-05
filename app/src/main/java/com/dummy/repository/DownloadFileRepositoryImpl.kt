package com.dummy.repository

import com.dummy.api.DownloadService
import com.dummy.domain.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody


internal class DownloadFileRepositoryImpl(private val downloadService: DownloadService) :
    DownloadFileRepository {

    override suspend fun download(): Status<ResponseBody> = withContext(Dispatchers.IO) {
        try {
            val response = downloadService.downloadResultPdf()
            Status.Success(response)

        } catch (exception: Exception) {
            Status.Error(exception.message ?: "")
        }
    }
}
