package com.dummy.pdfexport

import com.dummy.domain.CoroutineContextProvider
import com.dummy.domain.Status
import com.dummy.domain.StoreFileUseCase
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.*

class StoreFileUseCaseImpl(
    private val contextProvider: CoroutineContextProvider,
    private val notifyProgress: (Int)-> Unit

) : StoreFileUseCase {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun store(responseBody: ResponseBody, locationPath: String, fileName: String): Status<String> = withContext(contextProvider.DEFAULT) {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        val data = ByteArray(1024)
        var count: Int
        var progress = 0.0
        var percentage: Double
        val fileSize = responseBody.contentLength()
        val destinationFile = File(locationPath, fileName)
        try {
            inputStream = responseBody.byteStream()
            outputStream = FileOutputStream(destinationFile)
            while (inputStream.read(data).also { count = it } != -1) {
                outputStream.write(data, 0, count)
                progress += count
                percentage = (progress / fileSize) * 100
                notifyProgress.invoke(percentage.toInt())
            }
            outputStream.flush()
            Status.Success(destinationFile.path)
        } catch (e: IOException) {
            e.printStackTrace()
            notifyProgress.invoke(0)
            Status.Error(e.message?: "Unable to save file")
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }
}
