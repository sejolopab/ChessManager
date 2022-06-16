package com.example.ajedrez.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.firebase.client.ServerValue
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        fun hideKeyboard(view: View, context: Context) {
            val inputMethodManager = Objects.requireNonNull(context)
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun getLongTimeStamp(): Long {
            return System.currentTimeMillis()
        }

        fun getStringTimeStamp(timeStamp: Long): String {
            val sdf = SimpleDateFormat("d MMM, yyyy")
            val resultDate = Date(timeStamp)
            return sdf.format(resultDate)
        }
    }
}