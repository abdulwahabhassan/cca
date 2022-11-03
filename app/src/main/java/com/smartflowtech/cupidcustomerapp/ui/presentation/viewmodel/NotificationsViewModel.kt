package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.NotificationsRepository
import com.smartflowtech.cupidcustomerapp.model.domain.DaysFilter
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
            viewModelResult = ViewModelResult.INITIAL,
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
                flowOf(notificationsRepository.getNotifications())
            ) { prefs, notificationsResult ->
                Timber.d("Local date time now ${LocalDateTime.now()}")
                val notifications = notificationsResult.data.filter {
                    when (prefs.notificationsFilter) {
                        DaysFilter.TODAY.name -> {
                            LocalDateTime.parse(it.dateTime).toLocalDate() == LocalDateTime.now()
                                .toLocalDate()
                        }
                        DaysFilter.LAST_SEVEN_DAYS.name -> {
                            LocalDateTime.parse(it.dateTime).toLocalDate() >= LocalDateTime.now()
                                .toLocalDate().minusDays(7)
                        }
                        DaysFilter.LAST_THIRTY_DAYS.name -> {
                            LocalDateTime.parse(it.dateTime).toLocalDate() >= LocalDateTime.now()
                                .toLocalDate().minusDays(30)
                        }
                        else -> true
                    }
                }
                NotificationsScreenUiState(
                    viewModelResult = ViewModelResult.SUCCESS,
                    notifications = notifications
                )

            }.collectLatest {
                notificationsScreenUiState = it
            }

        }
    }

}