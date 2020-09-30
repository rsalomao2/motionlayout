package com.dummy.ui

import android.Manifest.permission
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dummy.PermissionController
import com.dummy.R
import com.dummy.pdfexport.DownloadFileUseCase
import com.dummy.pdfexport.PDFExportUseCaseImpl
import com.dummy.repository.TestsController
import kotlinx.android.synthetic.main.fragment_pdf_export.*


class PDFExportFragment : Fragment(R.layout.fragment_pdf_export) {
    private val testsController by lazy { TestsController() }
    private val downloadFileUseCase by lazy { DownloadFileUseCase() }
    private val pdfExportUseCase by lazy {
        PDFExportUseCaseImpl(
            downloadFileUseCase,
            testsController
        )
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
        val writeExternalPermission = permission.WRITE_EXTERNAL_STORAGE
        buttonTv.setOnClickListener {
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
            if (permissionController.hasPermission(writeExternalPermission))
                pdfViewModel.downloadPDF()
            else
                permissionController.requestPermission(arrayOf(writeExternalPermission), PERMISSION_WRITE_EXTERNAL_CODE)
        }
    }

    private fun setupObservers() {
        pdfViewModel.pdfDownloadedPath.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }
}
