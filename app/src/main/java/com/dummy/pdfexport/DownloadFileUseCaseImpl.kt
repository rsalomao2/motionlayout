package com.dummy.pdfexport

import com.dummy.domain.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal class DownloadFileUseCaseImpl : DownloadFileUseCase {

    override suspend fun download(
        fileUrl: String,
        fileNameWithExtension: String,
        folderPath: String
    ): Status<String> = withContext(Dispatchers.IO) {
        if (true)
            Status.Success("")
        else
            Status.Error("Error Message")
    }
}
