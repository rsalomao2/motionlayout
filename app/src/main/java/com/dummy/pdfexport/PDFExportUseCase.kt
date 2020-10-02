package com.dummy.pdfexport

import com.dummy.domain.Status

internal interface PDFExportUseCase {
    suspend fun getResultsPdf(userName: String, folderPath: String): Status<String>
}
