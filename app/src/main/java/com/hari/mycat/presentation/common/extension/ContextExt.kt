package com.hari.mycat.presentation.common.extension

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.hari.mycat.R

fun Context.showToastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showGenericAlertDialog(message: String) {
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.title_ok)) { dialog, _ -> dialog.dismiss() }
    }.show()
}