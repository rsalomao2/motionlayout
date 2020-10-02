package com.dummy.pdfexport

import com.dummy.domain.Status
import com.dummy.repository.TestsController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal class PDFExportUseCaseImpl(
    private val downloadFileUseCase: DownloadFileUseCase,
    private val testResultsController: TestsController
) : PDFExportUseCase {
    companion object {
        const val FILE_NAME = "HT_RESULTS.pdf"
    }


    override suspend fun getResultsPdf(userName: String, folderPath: String): Status<String> =
        withContext(Dispatchers.IO) {
            val testsResultsPDFUrl = testResultsController.getTestsResultsPDF(userName)
            downloadFileUseCase.download(testsResultsPDFUrl, "${userName}_$FILE_NAME", folderPath)
        }
}
