package com.dummy.pdfexport

import com.dummy.domain.CoroutineContextProvider
import com.dummy.domain.PDFExportUseCase
import com.dummy.domain.Status
import com.dummy.domain.StoreFileUseCase
import com.dummy.repository.DownloadFileRepository
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody


internal class PDFExportUseCaseImpl(
    private val contextProvider: CoroutineContextProvider,
    private val downloadFileRepository: DownloadFileRepository,
    private val storeFileUseCase: StoreFileUseCase
) : PDFExportUseCase {

    override suspend fun exportPdf(
        userName: String,
        folderPath: String,
        fileName: String
    ) = withContext(contextProvider.IO) {
        try {
            when (val response = downloadFileRepository.download()) {
                is Status.Success -> {
                    val responseBody = response.response
                    storeFileLocally(responseBody, folderPath, fileName)
                }
                is Status.Error -> Status.Error(response.responseError)
            }
        } catch (exception: Exception) {
            Status.Error(exception.message ?: "")
        }
    }

    private suspend fun storeFileLocally(
        responseBody: ResponseBody,
        folderPath: String,
        fileName: String
    ) = withContext(contextProvider.DEFAULT) {
        storeFileUseCase.store(responseBody, folderPath, fileName)
    }
}
