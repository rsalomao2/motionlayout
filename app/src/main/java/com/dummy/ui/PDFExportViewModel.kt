package com.dummy.ui

import android.Manifest.permission
import androidx.annotation.RequiresPermission
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dummy.pdfexport.PDFExportUseCase
import kotlinx.coroutines.launch

internal class PDFExportViewModel(private val pdfExportUseCase: PDFExportUseCase): ViewModel() {
    val pdfDownloadedPath = MutableLiveData<String>()

    @RequiresPermission(permission.WRITE_EXTERNAL_STORAGE)
    fun downloadPDF(){
        viewModelScope.launch{
            pdfDownloadedPath.value = pdfExportUseCase.getResultsPdf()
        }
    }
}