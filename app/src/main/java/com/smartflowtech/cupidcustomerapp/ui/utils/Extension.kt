package com.smartflowtech.cupidcustomerapp.ui.utils

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
        return this.split(" ").joinToString(" ") {
            it.capitalizeFirstLetter()
        }
    }
}