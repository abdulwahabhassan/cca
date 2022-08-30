package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

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
import com.google.accompanist.permissions.*
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Receipt(
    transaction: Transaction,
    onGoBackToTransactionListPressed: () -> Unit
) {
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

            if (multiplePermissionsState.allPermissionsGranted) {
                //Download button
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    onClick = {

                        val document = PdfDocument()
                        val bmp = BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.ic_smartflow_logo
                        )
                        val scaledBitmap = Bitmap.createScaledBitmap(bmp, 200, 60, false)

                        val titleGreyPaint = Paint()
                        titleGreyPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                        titleGreyPaint.textSize = 18F
                        titleGreyPaint.color = ContextCompat.getColor(context, R.color.grey)
                        titleGreyPaint.textAlign = Paint.Align.LEFT

                        val textDarkBluePaint = Paint()
                        textDarkBluePaint.typeface =
                            Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                        textDarkBluePaint.textSize = 22F
                        textDarkBluePaint.color = ContextCompat.getColor(context, R.color.darkBlue)
                        textDarkBluePaint.textAlign = Paint.Align.LEFT

                        val metaDataBlackPaint = Paint()
                        metaDataBlackPaint.typeface =
                            Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                        metaDataBlackPaint.textSize = 14F
                        metaDataBlackPaint.color = ContextCompat.getColor(context, R.color.black)

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

                        //Header
                        canvas.drawText("Receipt", 40F, 145F, headerBlackPaint)

                        //Body
                        canvas.drawText("Transaction Type", 109F, 200F, titleGreyPaint)
                        canvas.drawText(transaction.type, 109F, 225F, textDarkBluePaint)

                        canvas.drawText("Reference Number", 109F, 260F, titleGreyPaint)
                        canvas.drawText(transaction.referenceNumber, 109F, 285F, textDarkBluePaint)

                        canvas.drawText("Payment Date", 109F, 320F, titleGreyPaint)
                        canvas.drawText(transaction.date, 109F, 345F, textDarkBluePaint)
                        canvas.drawText(transaction.time, 109F, 365F, textDarkBluePaint)

                        canvas.drawText("Description", 109F, 400F, titleGreyPaint)
                        canvas.drawText(transaction.description, 109F, 425F, textDarkBluePaint)

                        canvas.drawText("Amount", 109F, 460F, titleGreyPaint)
                        canvas.drawText(transaction.amount, 109F, 485F, textDarkBluePaint)

                        canvas.drawText("Status", 109F, 520F, titleGreyPaint)
                        canvas.drawText(transaction.status, 109F, 545F, textDarkBluePaint)

                        document.finishPage(page)

                        val file = createFile(transaction.referenceNumber)

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
                        color = red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(
                                color = transparentPink,
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

@OptIn(ExperimentalPermissionsApi::class)
private fun getTextToShowGivenPermissions(
    permissions: List<PermissionState>,
    shouldShowRationale: Boolean
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder()

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission.substringAfter("android.permission."))
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }
            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }
            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    textToShow.append(
        if (shouldShowRationale) {
            " important. Please grant all of them for the app to function properly."
        } else {
            " denied. The app cannot function without them. Please grant these permissions."
        }
    )
    return textToShow.toString()
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
            {}
        )
    }
}