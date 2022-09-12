package com.smartflowtech.cupidcustomerapp.ui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.ui.text.toLowerCase
import timber.log.Timber
import java.util.*

object Extension {
    fun String.capitalizeFirstLetter(): String {
        return this.lowercase(Locale.ROOT).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
    }

    fun String.capitalizeEachWord(): String {
        return this.split(" ", "_").joinToString(" ") {
            it.capitalizeFirstLetter()
        }
    }

    fun Context.findActivity(): Activity {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        throw IllegalStateException("no activity")
    }

}