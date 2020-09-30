package com.dummy.pdfexport

internal interface PDFExportUseCase {
    suspend fun getResultsPdf(userName: String = ""): String?
}
