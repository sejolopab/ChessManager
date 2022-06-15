package com.example.ajedrez.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.firebase.client.ServerValue
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        fun hideKeyboard(view: View, context: Context) {
            val inputMethodManager = Objects.requireNonNull(context)
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun getCreationDate(): Map<String?, String?>? {
            return ServerValue.TIMESTAMP
        }

        fun getTimeDate(): Long {
            return System.currentTimeMillis()
        }
    }
}