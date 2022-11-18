package com.smartflowtech.cupidcustomerapp.model.domain


data class NotificationItem(
    val id: Long,

    val userID: String,

    val messageTitle: String,

    val messageBody: String,

    val createdAt: String,

    val updatedAt: String,

    val icon: Int
)
