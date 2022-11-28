package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.login.LoginState
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun DownloadTransactions(
    printTransactionReport: suspend (dateFrom: String, dateTo: String) -> PrintTransactionReportState,
    showSuccess: (String) -> Unit
) {

    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }
    var startDateErrorLabel by rememberSaveable { mutableStateOf("") }
    var endDateErrorLabel by rememberSaveable { mutableStateOf("") }
    var isStartDateError by rememberSaveable { mutableStateOf(false) }
    var isEndDateError by rememberSaveable { mutableStateOf(false) }
    var printFormatSelection by rememberSaveable {
        mutableStateOf("PDF")
    }
    val coroutineScope = rememberCoroutineScope()
    var showLoadingIndicator: Boolean by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val ctx = LocalContext.current
    val focusManager = LocalFocusManager.current


    fun resetErrorsAndLabels() {
        startDateErrorLabel = ""
        isStartDateError = false
        endDateErrorLabel = ""
        isEndDateError = false
    }

    fun validateInput(trimmedStartDate: String, trimmedEndDate: String) {
        if (trimmedStartDate.isEmpty() ||
            !trimmedStartDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
        ) {
            startDateErrorLabel = "Input valid date format"
            isStartDateError = true
        }

        if (trimmedEndDate.isEmpty() ||
            !trimmedEndDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
        ) {
            endDateErrorLabel = "Input valid date format"
            isEndDateError = true
        }
    }

    fun doPrintTransactionReport(dateFrom: String, dateTo: String) {
        coroutineScope.launch {
            val printTransactionReportState = printTransactionReport(dateFrom, dateTo)
            when (printTransactionReportState.viewModelResult) {
                ViewModelResult.ERROR -> {
                    showLoadingIndicator = false
                    if (printTransactionReportState.message?.isNotEmpty() == true) {
                        if (scaffoldState.snackbarHostState
                                .currentSnackbarData == null
                        ) {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = printTransactionReportState.message,
                                duration = SnackbarDuration.Short
                            )
                        }

                    }
                }
                ViewModelResult.SUCCESS -> {
                    showSuccess(
                        printTransactionReportState.message
                            ?: "Your request was sent. The report will be sent to your email"
                    )
                }
                else -> {}
            }
        }
    }


    Scaffold(
        modifier = Modifier
            .fillMaxWidth(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    backgroundColor = transparentPink,
                    contentColor = red,
                    snackbarData = data
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Input History Range", color = Color.Black,
                    fontFamily = AthleticsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Start date
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = startDate,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { text ->
                    startDate = text
                },
                placeholder = { Text("YYYY-MM-DD") },
                singleLine = true,
                label = {
                    if (isStartDateError) {
                        Text(text = startDateErrorLabel)
                    } else {
                        Text(text = "Start date")
                    }
                },
                isError = isStartDateError,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightGrey,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            //End date
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = endDate,
                onValueChange = { text ->
                    endDate = text
                },
                placeholder = { Text("YYYY-MM-DD") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {
                    if (isEndDateError) {
                        Text(text = endDateErrorLabel)
                    } else {
                        Text(text = "End date")
                    }
                },
                singleLine = true,
                isError = isEndDateError,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightGrey,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                //PDF
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .background(color = lightGrey, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            printFormatSelection = "PDF"
                        }
                        .border(
                            width = 1.dp,
                            color = if (printFormatSelection == "PDF") darkBlue else lightGrey,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = printFormatSelection == "PDF",
                        onClick = {
                            printFormatSelection = "PDF"
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = darkBlue)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "PDF", color = darkBlue)
                }

                Spacer(modifier = Modifier.width(16.dp))

                //Excel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = lightGrey, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            Toast
                                .makeText(
                                    ctx,
                                    "This option is currently unavailable",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                        .border(
                            width = 1.dp,
                            color = if (printFormatSelection == "Excel") darkBlue else lightGrey,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = printFormatSelection == "Excel",
                        onClick = {
                            Toast
                                .makeText(
                                    ctx,
                                    "This option is currently unavailable",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        },
                        enabled = false,
                        colors = RadioButtonDefaults.colors(selectedColor = darkBlue)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "Excel", color = darkBlue)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (showLoadingIndicator) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.height(54.dp)
                )
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    enabled = startDate.isNotEmpty() && endDate.isNotEmpty(),
                    onClick = {

                        resetErrorsAndLabels()

                        val trimmedStartDate = startDate.trim()
                        val trimmedEndDate = endDate.trim()

                        validateInput(trimmedStartDate, trimmedEndDate)

                        if (!isStartDateError && !isEndDateError) {
                            if (LocalDate.parse(trimmedStartDate) > LocalDate.parse(trimmedEndDate)) {
                                Toast.makeText(
                                    ctx,
                                    "Invalid input! Check dates",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                focusManager.clearFocus()
                                showLoadingIndicator = true
                                doPrintTransactionReport(
                                    dateFrom = trimmedStartDate,
                                    trimmedEndDate
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(text = "Request")
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
fun CustomDateSearchPreview() {
    CupidCustomerAppTheme {
        DownloadTransactions({ _, _ -> PrintTransactionReportState(ViewModelResult.SUCCESS) }, {})
    }
}