package com.dummy.domain

internal interface PDFExportUseCase {
    suspend fun exportPdf(userName: String, folderPath: String, fileName: String): Status<String>
}
