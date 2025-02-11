package com.smartflowtech.cupidcustomerapp.ui.presentation.notification_settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.ui.presentation.settings.AppearanceToggleButton
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.SettingsViewModel
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.theme.lineGrey
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
fun NotificationSettings(
    emailNotifications: Boolean,
    pushNotifications: Boolean,
    updateEmailNotification: (Boolean) -> Unit,
    updatePushNotification: (Boolean) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(top = 40.dp)) {
        items(items = Util.getListOfNotificationSettingsItems()) { item ->
            Column(
                modifier = Modifier
                    .clickable(false) {}
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = item.icon),
                            contentDescription = "${item.name} icon"
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = item.name, fontFamily = AthleticsFontFamily,
                                fontWeight = FontWeight.W400,
                                color = Color.Black
                            )
                            Text(text = item.description, fontSize = 14.sp, color = grey)
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    AppearanceToggleButton(toggleState = when (item.name) {
                        "Email Notifications" -> emailNotifications
                        "Push Notifications" -> pushNotifications
                        else -> false
                    }, onToggled = { bool ->
                        when (item.name) {
                            "Email Notifications" -> {
                                updateEmailNotification(bool)
                            }
                            "Push Notifications" -> {
                                updatePushNotification(bool)
                            }
                            else -> {}
                        }
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    color = lineGrey,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewNotificationSettings() {
    CupidCustomerAppTheme {
//        NotificationSettings()
    }
}