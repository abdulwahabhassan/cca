package com.smartflowtech.cupidcustomerapp.model.domain

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class MediaStoreImage(
    val id: Long,
    val contentUri: Uri,
    val displayName: String,
    val size: Int,
    val data: String
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaStoreImage>() {
            override fun areItemsTheSame(
                oldItem: MediaStoreImage,
                newItem: MediaStoreImage
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MediaStoreImage,
                newItem: MediaStoreImage
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}