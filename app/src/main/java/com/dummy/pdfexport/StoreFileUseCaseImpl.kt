package com.dummy.pdfexport

import com.dummy.domain.CoroutineContextProvider
import com.dummy.domain.Status
import com.dummy.domain.StoreFileUseCase
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.IOException

class StoreFileUseCaseImpl(private val contextProvider: CoroutineContextProvider) : StoreFileUseCase {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun store(
        responseBody: ResponseBody,
        locationPath: String,
        fileName: String
    ): Status<String> = withContext(contextProvider.DEFAULT) {

        val destinationFile = File(locationPath, fileName)
        val inputStream = responseBody.byteStream()

        try {
            inputStream.use {
                destinationFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            Status.Success(destinationFile.path)
        } catch (e: IOException) {
            Status.Error(e.message ?: "Unable to save file")
        }
    }
}
