package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.lightGrey
import kotlinx.coroutines.launch

@Composable
fun CustomDateSearch() {

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
                Text(text = "PDF")
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
                Text(text = "Excel")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

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
                    !trimmedStartDate.matches(Regex("\\d{2}-\\d{2}-\\d{4}"))
                ) {
                    startDateErrorLabel = "Input valid date format"
                    isStartDateError = true
                } else {
                    startDateErrorLabel = ""
                    isStartDateError = false
                }

                if (trimmedEndDate.isEmpty() ||
                    !trimmedEndDate.matches(Regex("\\d{2}-\\d{2}-\\d{4}"))
                ) {
                    endDateErrorLabel = "Input valid date format"
                    isEndDateError = true
                } else {
                    endDateErrorLabel = ""
                    isEndDateError = false
                }

                if (!isStartDateError && !isEndDateError) {

                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(text = "Login")
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
fun CustomDateSearchPreview() {
    CupidCustomerAppTheme {
        CustomDateSearch()
    }
}