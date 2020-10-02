package com.dummy.ui

import android.Manifest.permission
import android.os.Environment
import androidx.annotation.RequiresPermission
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dummy.domain.Status
import com.dummy.pdfexport.PDFExportUseCase
import kotlinx.coroutines.launch

internal class PDFExportViewModel(private val pdfExportUseCase: PDFExportUseCase) : ViewModel() {
    val pdfDownloadedPath = MutableLiveData<String?>()
    val errorMessage = MutableLiveData<String>()
    private val downloadFolderPath = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DOWNLOADS
    ).path

    @RequiresPermission(permission.WRITE_EXTERNAL_STORAGE)
    fun downloadPDF() {
        viewModelScope.launch {
            when (val downloadResult = pdfExportUseCase.getResultsPdf("", downloadFolderPath)) {
                is Status.Success -> pdfDownloadedPath.value = downloadResult.response
                is Status.Error -> errorMessage.value = downloadResult.responseError
            }
        }
    }
}