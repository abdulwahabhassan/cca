package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    dataStorePrefsRepository: DataStorePrefsRepository
) : BaseViewModel(dataStorePrefsRepository)