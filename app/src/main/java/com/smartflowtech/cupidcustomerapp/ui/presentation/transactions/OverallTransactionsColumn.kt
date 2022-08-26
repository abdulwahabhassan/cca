package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun OverallTransactionsColumn(onSearchBarClicked: () -> Unit) {
//
//    val coroutineScope = rememberCoroutineScope()
//    val modalBottomSheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//        animationSpec = spring()
//    )
//
//    Column {
//        Transactions({}, onSearchBarClicked = onSearchBarClicked)
//        FilterTransactionsModalBottomSheet(modalBottomSheetState = modalBottomSheetState) {
//            coroutineScope.launch {
//                if (modalBottomSheetState.isVisible) modalBottomSheetState.hide()
//            }
//        }
//    }
//
//}