package com.smartflowtech.cupidcustomerapp.ui.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.model.UploadImageOption
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.theme.lineGrey
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
fun UploadImage() {

    val options = Util.getListOfUploadImageOptions()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        userScrollEnabled = true,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Upload Image",
                    color = Color.Black,
                    fontFamily = AthleticsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(options) { item ->
            UploadImageOption(
                item
            ) { data: UploadImageOption ->

            }
        }
    }
}

@Composable
fun UploadImageOption(data: UploadImageOption, onClick: (transaction: UploadImageOption) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick(data)
                }
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(
                    id = data.icon
                ),
                contentDescription = "Icon",
                tint = Color.Unspecified
            )

            Column(
                Modifier
                    .weight(2f, true)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = data.title,
                    fontFamily = AthleticsFontFamily,
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = data.description,
                        fontSize = 14.sp,
                        color = grey
                    )
                }
            }
        }
        Divider(
            color = lineGrey,
            thickness = 0.5.dp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UploadImageOptionPreview() {
    CupidCustomerAppTheme {
        UploadImage ()
    }

}
