package com.dummy.pdfexport

import com.dummy.domain.Status

interface DownloadFileUseCase {

    suspend fun download( fileUrl: String,
                          fileNameWithExtension: String,
                          folderPath: String): Status<String>
}
