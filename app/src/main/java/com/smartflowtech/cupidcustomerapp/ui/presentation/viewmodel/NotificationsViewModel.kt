package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.NotificationsRepository
import com.smartflowtech.cupidcustomerapp.model.domain.Period
import com.smartflowtech.cupidcustomerapp.model.response.mapToNotificationItem
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.notification.NotificationsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val notificationsRepository: NotificationsRepository,
) : BaseViewModel(dataStorePrefsRepository) {

    var notificationsScreenUiState by mutableStateOf(
        NotificationsScreenUiState(
            viewModelResult = ViewModelResult.LOADING,
            notifications = emptyList()
        )
    )
        private set

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            combine(
                dataStorePrefsRepository.appConfigPreferencesAsFlow,
                flowOf(
                    notificationsRepository.getNotifications(
                        appConfigPreferences.token,
                        appConfigPreferences.userId
                    )
                )
            ) { prefs, notificationsResult ->
                Timber.d("Local date time now ${LocalDateTime.now()}")

                when (notificationsResult) {
                    is RepositoryResult.Success -> {
                        val notifications = notificationsResult.data?.filter {
                            when (prefs.notificationPeriodFilter) {
                                Period.TODAY.name -> {
                                    LocalDateTime.parse(it.createdAt)
                                        .toLocalDate() == LocalDateTime.now()
                                        .toLocalDate()
                                }
                                Period.ONE_WEEK.name -> {
                                    LocalDateTime.parse(it.createdAt)
                                        .toLocalDate() >= LocalDateTime.now()
                                        .toLocalDate().minusDays(7)
                                }
                                Period.ONE_MONTH.name -> {
                                    LocalDateTime.parse(it.createdAt)
                                        .toLocalDate() >= LocalDateTime.now()
                                        .toLocalDate().minusDays(30)
                                }
                                else -> true
                            }
                        }

                        NotificationsScreenUiState(
                            viewModelResult = ViewModelResult.SUCCESS,
                            notifications = notifications?.map { it.mapToNotificationItem() }
                                ?: emptyList()
                        )
                    }
                    is RepositoryResult.Error -> {
                        NotificationsScreenUiState(
                            viewModelResult = ViewModelResult.ERROR,
                            message = notificationsResult.message,
                            notifications = emptyList()
                        )
                    }
                }
            }.collectLatest {
                notificationsScreenUiState = it
            }

        }
    }

}