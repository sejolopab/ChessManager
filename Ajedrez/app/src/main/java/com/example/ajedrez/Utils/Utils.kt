package com.example.ajedrez.Utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*


class Utils {
    companion object {
        fun hideKeyboard(view: View, context: Context) {
            val inputMethodManager = Objects.requireNonNull(context)
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}