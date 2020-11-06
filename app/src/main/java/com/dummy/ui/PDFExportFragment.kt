package com.dummy.ui

import android.Manifest.permission
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dummy.PermissionController
import com.dummy.R
import com.dummy.api.DownloadService
import com.dummy.api.RetrofitBuilder
import com.dummy.di.CoroutineContextProviderImpl
import com.dummy.pdfexport.PDFExportUseCaseImpl
import com.dummy.pdfexport.StoreFileUseCaseImpl
import com.dummy.repository.DownloadFileRepositoryImpl
import kotlinx.android.synthetic.main.fragment_pdf_export.*

class PDFExportFragment : Fragment(R.layout.fragment_pdf_export) {

    private val writeExternalPermission = permission.WRITE_EXTERNAL_STORAGE
    private val service by lazy { RetrofitBuilder().build(DownloadService::class.java) }
    private val downloadFileUseCase by lazy { DownloadFileRepositoryImpl(service) }
    private val storeFileUseCase by lazy {
        StoreFileUseCaseImpl(CoroutineContextProviderImpl())
    }
    private val pdfExportUseCase by lazy {
        PDFExportUseCaseImpl(CoroutineContextProviderImpl(), downloadFileUseCase, storeFileUseCase)
    }
    private val pdfViewModel: PDFExportViewModel by viewModels {
        PDFExportViewModelFactory(
            pdfExportUseCase
        )
    }
    private val permissionController by lazy { PermissionController(this) }

    companion object {
        private const val PERMISSION_WRITE_EXTERNAL_CODE = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupViews()
    }

    @SuppressLint("MissingPermission")
    private fun setupViews() {
        button.setOnClickListener {
            txtProgressPercent.text = "Downloaded 0%"
            if (permissionController.hasPermission(writeExternalPermission))
                pdfViewModel.downloadPDF()
            else
                permissionController.requestPermission(
                    arrayOf(writeExternalPermission),
                    PERMISSION_WRITE_EXTERNAL_CODE
                )
        }
    }

    private fun setupObservers() {
        pdfViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun progressSaving(progress: Int) {
        progressBar.progress = progress
        txtProgressPercent.post {
            txtProgressPercent.text = "Downloaded $progress%"
        }
    }
}
