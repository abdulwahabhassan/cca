package com.smartflowtech.cupidcustomerapp.ui.presentation.notification

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.NotificationItem
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Header
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.theme.lightGrey
import com.smartflowtech.cupidcustomerapp.ui.theme.lineGrey
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import java.time.LocalDateTime


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun NotificationsList(
) {

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.Start
    ) {

        val newNotifications =
            Util.getListOfNotifications().filter {
                LocalDateTime.parse(it.dateTime).toLocalDate() == LocalDateTime.now().toLocalDate()
            }
        val olderNotifications =
            Util.getListOfNotifications().filter {
                LocalDateTime.parse(it.dateTime).toLocalDate() != LocalDateTime.now().toLocalDate()
            }
        val notifications = mapOf(
            "New" to newNotifications,
            "Older" to olderNotifications
        )

        if (notifications.values.isEmpty()) {
            Column(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "No notification icon",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "No notification")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                userScrollEnabled = true,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                notifications.forEach { (header, notifications) ->
                    stickyHeader {
                        Header(header, bgColor = lineGrey, fontWeight = FontWeight.Bold)
                    }

                    items(notifications) { item ->
                        Notification(
                            item
                        ) { data: NotificationItem ->

                        }
                    }

                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewNotificationsList() {
    CupidCustomerAppTheme {
        NotificationsList()
    }
}
