package com.example.contactlistjc.ui.components

import android.app.AlertDialog
import android.content.Context

fun showErrorDialog(context: Context, message: String, onPositiveButtonClick: () -> Unit = {}) {
    AlertDialog.Builder(context)
        .setTitle("Error")
        .setMessage(message)
        .setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            onPositiveButtonClick()
        }
        .show()
}
