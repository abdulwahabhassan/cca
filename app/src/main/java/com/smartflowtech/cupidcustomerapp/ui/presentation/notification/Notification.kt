package com.smartflowtech.cupidcustomerapp.ui.presentation.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.smartflowtech.cupidcustomerapp.model.domain.NotificationItem
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Notification(data: NotificationItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
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
                    text = data.messageBody,
                    fontFamily = AthleticsFontFamily,
                    fontWeight = FontWeight.W400
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = LocalDateTime.parse(
                            data.createdAt,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        ).format(DateTimeFormatter.ofPattern("E, dd MMM yyyy")).toString(),
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
//        Notification(
//
//        )
    }

}
