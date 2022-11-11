package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.StationsRepository
import com.smartflowtech.cupidcustomerapp.model.request.VendorStationsRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.station.StationsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationsViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val stationsRepository: StationsRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var stationsScreenUiState by mutableStateOf(StationsScreenUiState(ViewModelResult.LOADING))
        private set

    fun vendorStations() {
        viewModelScope.launch {

            stationsScreenUiState = StationsScreenUiState(viewModelResult = ViewModelResult.LOADING)

            when (val repositoryResult = stationsRepository.vendorStations(
                token = appConfigPreferences.token,
                vendorId = appConfigPreferences.companyId.toLong(),
                vendorStationsRequestBody = VendorStationsRequestBody(
                    appConfigPreferences.companyId.toLong()
                )
            )
            ) {
                is RepositoryResult.Success -> {
                    repositoryResult.data?.let { data ->
                        stationsScreenUiState =
                            StationsScreenUiState(
                                viewModelResult = ViewModelResult.SUCCESS,
                                data = data,
                                message = repositoryResult.message
                            )
                    }
                }
                is RepositoryResult.Error -> {
                    stationsScreenUiState = StationsScreenUiState(
                        viewModelResult = ViewModelResult.ERROR,
                        message = repositoryResult.message
                    )
                }
            }
        }
    }

}