package com.smartflowtech.cupidcustomerapp.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.smartflowtech.cupidcustomerapp.ui.presentation.activity.MediaStoreActivity

class OpenMediaStore : ActivityResultContract<Unit, String>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, MediaStoreActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
        val imageUri = intent?.getStringExtra("imageURI")
        return if (resultCode == Activity.RESULT_OK && imageUri != null) imageUri else ""
    }

}