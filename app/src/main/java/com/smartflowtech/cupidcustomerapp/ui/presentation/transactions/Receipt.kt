package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@Composable
fun Receipt(
    transaction: Transaction,
    onGoBackToTransactionListPressed: () -> Unit,
    onDownloadTransactionPressed: (transaction: Transaction) -> Unit
) {
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
                text = "Transaction Type",
                color = grey,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = transaction.type,
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Reference Number",
                color = grey,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = transaction.referenceNumber,
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Payment Date", color = grey, modifier = Modifier.padding(bottom = 2.dp))
            Text(
                text = transaction.date,
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = transaction.time,
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Description", color = grey, modifier = Modifier.padding(bottom = 2.dp))
            Text(
                text = transaction.description,
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Amount", color = grey, modifier = Modifier.padding(bottom = 2.dp))
            Text(
                text = transaction.amount,
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Status", color = grey, modifier = Modifier.padding(bottom = 2.dp))
            Text(
                text = transaction.status,
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(32.dp))

            //Download button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                onClick = {
                    onDownloadTransactionPressed(transaction)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text(text = "Download")
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
                onGoBackToTransactionListPressed()
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back arrow",
                    tint = darkBlue,
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Go back",
                color = darkBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun ReceiptPreview() {
    CupidCustomerAppTheme {
        Receipt(
            transaction = Transaction(
                type = "Mobile Transfer",
                referenceNumber = "TRS90399291",
                date = "2022-08-31 08:21AM",
                description = "Purchase on Cupid",
                amount = "₦20,500.05",
                status = "Successful",
                time = "08:21AM",
                title = "Mobile Transfer"
            ),
            {},
            {}
        )
    }
}