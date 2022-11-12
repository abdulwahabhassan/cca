package com.smartflowtech.cupidcustomerapp.ui.presentation.station

import com.smartflowtech.cupidcustomerapp.model.response.VendorStationsData
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class StationsScreenUiState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: VendorStationsData? = null
)
