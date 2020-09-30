package com.dummy.pdfexport

import android.Manifest
import com.dummy.PermissionController
import com.dummy.repository.TestsController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal class PDFExportUseCaseImpl(
    private val downloadFileUseCase: DownloadFileUseCase,
//        private val shareContentUseCase: ShareContentUseCase,
    private val testResultsController: TestsController
) : PDFExportUseCase {
    companion object {
        const val FILE_NAME = "HT_RESULTS.pdf"
    }

    override suspend fun getResultsPdf(userName: String): String? = withContext(Dispatchers.IO) {
        val testsResultsPDFUrl = testResultsController.getTestsResultsPDF(userName)
        downloadFileUseCase.download(testsResultsPDFUrl, "${userName}_$FILE_NAME")
    }
}

//internal class ShareContentUseCase(private val context: Context) {
//    fun share(filePath: String, fileNameWithExtension: String) {
//        val outputFile = File(filePath, fileNameWithExtension)
//        val uriToImage = FileProvider.getUriForFile(context, FILES_AUTHORITY, outputFile)
//        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
//                .setStream(uriToImage)
//                .getIntent();
//// Provide read access
//        shareIntent.setData(uriToImage);
//        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        context.startActivity(share)
//    }
//}
