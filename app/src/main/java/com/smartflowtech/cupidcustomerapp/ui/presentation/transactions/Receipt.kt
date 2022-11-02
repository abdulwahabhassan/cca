package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.accompanist.permissions.*
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.Status
import com.smartflowtech.cupidcustomerapp.model.domain.Transaction
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import com.smartflowtech.cupidcustomerapp.ui.utils.Util.getTextToShowGivenPermissions
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Receipt(
    transaction: Transaction,
    onGoBackToTransactionListPressed: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    )

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
                text = transaction.authType ?: "",
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
                text = transaction.transactionSeqNumber ?: "",
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Payment Period", color = grey, modifier = Modifier.padding(bottom = 2.dp))
            Text(
                text = transaction.date ?: "",
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = transaction.time ?: "",
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Description", color = grey, modifier = Modifier.padding(bottom = 2.dp))
            Text(
                text = transaction.vendorStationName ?: "",
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Amount", color = grey, modifier = Modifier.padding(bottom = 2.dp))
            Text(
                text = """₦${transaction.amount?.let { Util.formatAmount(it) }}""",
                color = darkBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Status", color = grey, modifier = Modifier.padding(bottom = 2.dp))
            Text(
                text = transaction.status ?: "",
                color = when (transaction.status) {
                    Status.COMPLETED.name.capitalizeFirstLetter() -> green
                    Status.PENDING.name.capitalizeFirstLetter() -> darkBlue
                    Status.FAILED.name.capitalizeFirstLetter() -> red
                    else -> {
                        darkBlue
                    }
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(32.dp))

            if (multiplePermissionsState.allPermissionsGranted) {
                //Download button
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    onClick = {

                        coroutineScope.launch {
                            //Create pdf
                            val document = PdfDocument()
                            val bmp = BitmapFactory.decodeResource(
                                context.resources,
                                R.drawable.ic_smartflow_logo
                            )
                            val scaledBitmap = Bitmap.createScaledBitmap(bmp, 200, 60, false)

                            val titleGreyPaint = Paint()
                            titleGreyPaint.typeface =
                                Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                            titleGreyPaint.textSize = 18F
                            titleGreyPaint.color = ContextCompat.getColor(context, R.color.grey)
                            titleGreyPaint.textAlign = Paint.Align.LEFT

                            val textDarkBluePaint = Paint()
                            textDarkBluePaint.typeface =
                                Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                            textDarkBluePaint.textSize = 22F
                            textDarkBluePaint.color =
                                ContextCompat.getColor(context, R.color.darkBlue)
                            textDarkBluePaint.textAlign = Paint.Align.LEFT

                            val metaDataBlackPaint = Paint()
                            metaDataBlackPaint.typeface =
                                Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                            metaDataBlackPaint.textSize = 14F
                            metaDataBlackPaint.color =
                                ContextCompat.getColor(context, R.color.black)

                            val urlBluePaint = Paint()
                            urlBluePaint.typeface =
                                Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                            urlBluePaint.textSize = 12F
                            urlBluePaint.color = ContextCompat.getColor(context, R.color.darkBlue)

                            val headerBlackPaint = Paint()
                            headerBlackPaint.typeface =
                                Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                            headerBlackPaint.textSize = 22F
                            headerBlackPaint.color = ContextCompat.getColor(context, R.color.black)
                            headerBlackPaint.textAlign = Paint.Align.LEFT

                            val pageInfo = PdfDocument.PageInfo.Builder(600, 600, 1).create()
                            val page = document.startPage(pageInfo)

                            val canvas = page.canvas
                            canvas.drawBitmap(scaledBitmap, 40F, 35F, Paint())
                            canvas.drawText(
                                "Smartflow Technologies Ltd.",
                                109F,
                                65F,
                                metaDataBlackPaint
                            )
                            canvas.drawText("www.smartflowtech.com", 109F, 85F, urlBluePaint)

                            canvas.drawText("Receipt", 40F, 145F, headerBlackPaint)

                            canvas.drawText("Transaction Type", 109F, 200F, titleGreyPaint)
                            canvas.drawText(
                                transaction.authType ?: "",
                                109F,
                                225F,
                                textDarkBluePaint
                            )

                            canvas.drawText("Reference Number", 109F, 260F, titleGreyPaint)
                            canvas.drawText(
                                transaction.transactionSeqNumber ?: "",
                                109F,
                                285F,
                                textDarkBluePaint
                            )

                            canvas.drawText("Payment Date", 109F, 320F, titleGreyPaint)
                            canvas.drawText(
                                buildString {
                                    append(
                                        LocalDate.parse(transaction.date)
                                            .format(DateTimeFormatter.ofPattern("E, dd MMM yyyy"))
                                            ?: ""
                                    )
                                    append(", ${transaction.time}")
                                },
                                109F,
                                345F,
                                textDarkBluePaint
                            )

                            canvas.drawText("Description", 109F, 380F, titleGreyPaint)
                            canvas.drawText(transaction.title ?: "", 109F, 405F, textDarkBluePaint)

                            canvas.drawText("Amount", 109F, 440F, titleGreyPaint)
                            canvas.drawText(
                                """₦${transaction.amount?.let { Util.formatAmount(it) }}""" ?: "",
                                109F,
                                465F,
                                textDarkBluePaint
                            )

                            canvas.drawText("Status", 109F, 500F, titleGreyPaint)
                            canvas.drawText(transaction.status ?: "", 109F, 525F, textDarkBluePaint)

                            document.finishPage(page)

                            //Save pdf to storage
                            val file = createFile(transaction.transactionSeqNumber ?: "")

                            try {
                                document.writeTo(FileOutputStream(file))
                                Toast.makeText(context, "Downloaded as PDF", Toast.LENGTH_SHORT)
                                    .show()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(
                                    context,
                                    "Failed to generate PDF",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            document.close()

                            //Open the saved pdf
                            val pdfUri = FileProvider.getUriForFile(
                                context,
                                context.applicationContext.packageName + ".provider",
                                file
                            )
                            val pdfIntent = Intent(Intent.ACTION_VIEW)
                            pdfIntent.setDataAndType(pdfUri, "application/pdf")
                            pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            pdfIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                            try {
                                context.startActivity(pdfIntent)
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    e.localizedMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }


                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(text = "Download")
                }
            } else {
                Column {
                    Text(
                        getTextToShowGivenPermissions(
                            multiplePermissionsState.revokedPermissions,
                            multiplePermissionsState.shouldShowRationale
                        ),
                        color = darkBlue,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(
                                color = transparentBlue,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                        Text("Grant permission")
                    }
                }
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


private fun createFile(fileName: String): File {
    val title = "SFT$fileName.pdf"
    val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(path, title)
    if (!file.exists()) file.createNewFile()
    return file
}


@Composable
@Preview(showBackground = true)
fun ReceiptPreview() {
    CupidCustomerAppTheme {
        Receipt(
            transaction = Transaction(
                authType = "Mobile Transfer",
                transactionSeqNumber = "TRS90399291",
                date = "2022-08-31 08:21AM",
                vendorStationName = "Purchase on CUPID_PROD",
                amount = "₦20,500.05",
                status = "Successful",
                time = "08:21AM",
                title = "Mobile Transfer",
                product = "DPK",
                nfcTagCode = "VLX-5324"
            ),
            {}
        )
    }
}