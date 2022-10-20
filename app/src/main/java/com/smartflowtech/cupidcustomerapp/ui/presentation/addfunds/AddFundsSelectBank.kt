package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.Bank
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.SearchBar
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.HomeScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
fun AddFundsSelectBank(onBankSelected: (Bank) -> Unit) {

//    val context = LocalContext.current
    var queryText by rememberSaveable { mutableStateOf("") }
    val banks by remember(queryText) {
        val list = Util.getListOfBanks().filter { bank ->
            (bank.name?.contains(queryText, true) == true)
        }
        mutableStateOf(list)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Choose Payment Bank",
            color = Color.Black,
            fontFamily = AthleticsFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            query = queryText,
            onQueryChange = { searchText ->
                queryText = searchText

            },
            searchPlaceholder = "Search bank"
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(banks) { bank: Bank ->
                AddFundsBank(bank = bank, onClick = {
                    onBankSelected(it)
                })
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun AddFundsSelectBankPreview() {
    CupidCustomerAppTheme {
        AddFundsSelectBank(onBankSelected = {})
    }
}