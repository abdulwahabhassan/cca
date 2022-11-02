package com.smartflowtech.cupidcustomerapp.ui.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.android.upload.viewmodel.MediaStoreViewModel
import com.smartflowtech.cupidcustomerapp.databinding.ActivityMediaStoreBinding
import com.smartflowtech.cupidcustomerapp.model.domain.MediaStoreImage
import com.smartflowtech.cupidcustomerapp.ui.presentation.adapter.MediaStoreAdapter

class MediaStoreActivity : ComponentActivity() {
    private lateinit var binding: ActivityMediaStoreBinding
    private val viewModel: MediaStoreViewModel by viewModels()
    private lateinit var adapter: MediaStoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaStoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = MediaStoreAdapter { image ->
            returnToMainScreen(image)
        }
        binding.mediaStoreRecyclerView.adapter = adapter

        viewModel.loadImages()

        viewModel.images.observe(this) { images ->
            adapter.submitList(images)
        }
    }

    private fun returnToMainScreen(image: MediaStoreImage) {
        val intent = Intent().apply {
            putExtra("imageURI", image.data)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}