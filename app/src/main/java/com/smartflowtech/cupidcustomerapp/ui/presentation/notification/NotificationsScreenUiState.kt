package com.smartflowtech.cupidcustomerapp.ui.presentation.notification

import com.smartflowtech.cupidcustomerapp.model.domain.NotificationItem
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class NotificationsScreenUiState(
    val viewModelResult: ViewModelResult,
    val notifications: List<NotificationItem>,
    val message: String? = null
)