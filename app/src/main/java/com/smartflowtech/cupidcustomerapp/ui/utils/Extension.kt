package com.smartflowtech.cupidcustomerapp.ui.utils

import androidx.compose.ui.text.toLowerCase
import timber.log.Timber
import java.util.*

object Extension {
    fun String.capitalizeFirstLetter(): String {
        val result = this.lowercase(Locale.ROOT).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        Timber.d(result)
        return result
    }
}