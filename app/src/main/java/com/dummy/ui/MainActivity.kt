package com.dummy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dummy.R
import com.dummy.pdfexport.DownloadFileUseCase
import com.dummy.pdfexport.PDFExportUseCaseImpl
import com.dummy.repository.TestsController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val downloadFileUseCase = DownloadFileUseCase()
        val testResultController = TestsController()
        val pdfExportUseCase = PDFExportUseCaseImpl(downloadFileUseCase, testResultController)
        buttonTv.setOnClickListener { view ->
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
            GlobalScope.launch(Dispatchers.IO) {
                val resultsPdf = pdfExportUseCase.getResultsPdf()
                withContext(Dispatchers.Main) {
                    Toast.makeText(view.context, resultsPdf, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}