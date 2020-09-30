package com.dummy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dummy.pdfexport.PDFExportUseCase

internal class PDFExportViewModelFactory(private val pdfExportUseCase: PDFExportUseCase) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = PDFExportViewModel(pdfExportUseCase) as T
}
