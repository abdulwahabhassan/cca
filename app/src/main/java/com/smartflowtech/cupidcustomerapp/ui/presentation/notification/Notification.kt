package com.smartflowtech.cupidcustomerapp.ui.presentation.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.NotificationItem
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import java.time.LocalDateTime

@Composable
fun Notification(data: NotificationItem, onClick: (transaction: NotificationItem) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick(data)
                }
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),

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
                    text = data.message,
                    fontFamily = AthleticsFontFamily,
                    fontWeight = FontWeight.W400
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = LocalDateTime.parse(data.dateTime).toString().replace("T", " "),
                        fontSize = 12.sp,
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
fun NotificationPreview() {
    CupidCustomerAppTheme {
        Notification(
            Util.getListOfNotifications()[0],
            {}
        )
    }

}
