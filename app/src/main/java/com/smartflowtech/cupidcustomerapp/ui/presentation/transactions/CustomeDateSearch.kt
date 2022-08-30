package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.lightGrey

@Composable
fun CustomDateSearch(onGoBackToFilterPressed: () -> Unit, onShowSuccess: () -> Unit) {

    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }
    var startDateErrorLabel by rememberSaveable { mutableStateOf("") }
    var endDateErrorLabel by rememberSaveable { mutableStateOf("") }
    var isStartDateError by rememberSaveable { mutableStateOf(false) }
    var isEndDateError by rememberSaveable { mutableStateOf(false) }
    var printFormatSelection by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {

            Text(
                "Input Date Range", color = Color.Black,
                fontFamily = AthleticsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

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
                            printFormatSelection = "Excel"
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = darkBlue)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "Excel", color = darkBlue)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            //Print button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = startDate.isNotEmpty() && endDate.isNotEmpty(),
                onClick = {
                    val trimmedStartDate = startDate.trim()
                    val trimmedEndDate = endDate.trim()

                    //Basic validator
                    if (trimmedStartDate.isEmpty() ||
                        !trimmedStartDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
                    ) {
                        startDateErrorLabel = "Input valid date format"
                        isStartDateError = true
                    } else {
                        startDateErrorLabel = ""
                        isStartDateError = false
                    }

                    if (trimmedEndDate.isEmpty() ||
                        !trimmedEndDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
                    ) {
                        endDateErrorLabel = "Input valid date format"
                        isEndDateError = true
                    } else {
                        endDateErrorLabel = ""
                        isEndDateError = false
                    }

                    if (!isStartDateError && !isEndDateError) {
                        onShowSuccess()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text(text = "Print")
            }
        }

        //Go back arrow
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onGoBackToFilterPressed()
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back arrow",
                    tint = darkBlue,
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Go back",
                color = darkBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
fun CustomDateSearchPreview() {
    CupidCustomerAppTheme {
        CustomDateSearch({}, {})
    }
}