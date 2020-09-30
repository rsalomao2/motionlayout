package com.dummy

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * PermissionController lets you request permission for the system.
 */
class PermissionController(private val fragment: Fragment) {
    private var permissions: Array<String>? = null
    private var requestCode: Int = -1

    /**
     * Requests a list of permissions to be granted to this application
     *
     * @param permissions list of permission to be requested
     * @param requestCode code linked to the list of the requested permissions
     */
    fun requestPermission(permissions: Array<String>, requestCode: Int) {
        this.permissions = permissions
        this.requestCode = requestCode
        fragment.requestPermissions(getPermissionsDenied(), requestCode)
    }

    /**
     * Check the given if given permission is granted
     *
     * @param permission permission to be checked
     */
    fun hasPermission(permission: String): Boolean =
            ContextCompat.checkSelfPermission(fragment.requireContext(), permission) == PackageManager.PERMISSION_GRANTED

    /**
     * Check when user clicked on Never Ask Again checkbox
     */
    fun userCheckNeverAskAgain() =
            !ActivityCompat.shouldShowRequestPermissionRationale(fragment.requireActivity(),
                    getPermissionsDenied().first())

    /**
     * Handler to show explanation dialog in case user deny access to requested permission
     *
     * @param explanationData data with text to be used on dialog
     */
    fun showPermissionExplanation(explanationData: ExplanationData, negativeAction: () -> Unit) = fragment.context?.let { ctx ->
        AlertDialog.Builder(ctx).apply {
            setTitle(explanationData.title)
            setMessage(explanationData.message)
            setNegativeButton(explanationData.cancelActionString) { _, _ -> negativeAction.invoke() }
            setPositiveButton(explanationData.requestPermissionActionString) { _, _ -> requestPermission(getPermissionsDenied(), requestCode) }
        }.show()
    }

    /**
     * Handler to show explanation dialog in case user deny access
     * and checked Never Ask Again checkbox to requested permission
     * @param explanationData data with text to be used on dialog
     * @param negativeAction action to be executed on click in negative button
     */
    fun showSettingsExplanation(explanationData: ExplanationData, negativeAction: () -> Unit) = fragment.context?.let { ctx ->
        AlertDialog.Builder(ctx).apply {
            setTitle(explanationData.title)
            setMessage(explanationData.message)
            setNegativeButton(explanationData.cancelActionString) { _, _ -> negativeAction.invoke() }
            setPositiveButton(explanationData.openSettingsActionString) { _, _ -> openSettings() }
        }.show()
    }

    private fun getPermissionsDenied() = this.permissions?.filter {
        (ContextCompat.checkSelfPermission(fragment.requireContext(), it)
                != PackageManager.PERMISSION_GRANTED)
    }?.toTypedArray() ?: arrayOf()

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", fragment.context?.packageName, null)
        intent.data = uri
        fragment.requireActivity().startActivity(intent)
    }
}

/**
 * Data class that hold the Explanations Dialog text
 */
data class ExplanationData(@StringRes val title: Int, @StringRes val message: Int, @StringRes val cancelActionString: Int, @StringRes var requestPermissionActionString: Int, @StringRes var openSettingsActionString: Int)
