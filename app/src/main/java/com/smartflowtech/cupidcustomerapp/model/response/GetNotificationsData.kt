package com.smartflowtech.cupidcustomerapp.model.response

import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.NotificationItem
import com.squareup.moshi.Json

typealias GetNotificationsData = List<NotificationData>

data class NotificationData(
    val id: Long,

    @Json(name = "user_id")
    val userID: String,

    @Json(name = "message_title")
    val messageTitle: String,

    @Json(name = "message_body")
    val messageBody: String,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String
)

fun NotificationData.mapToNotificationItem(): NotificationItem {
    return NotificationItem(
        this.id,
        this.userID,
        this.messageTitle,
        this.messageBody,
        this.createdAt,
        this.updatedAt,
        R.drawable.ic_debit_notification
    )
}