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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.ui.presentation.settings.AppearanceToggleButton
import com.smartflowtech.cupidcustomerapp.ui.theme.lineGrey
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
@Preview(showBackground = true)
fun NotificationSettings() {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items = Util.getListOfNotificationSettingsItems()) { item ->
            Column(
                modifier = Modifier
                    .clickable {
                        //
                    }
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
                            Text(text = item.name, fontWeight = FontWeight.Bold)
                            Text(text = item.description, fontSize = 14.sp)
                        }
                    }
                    var toggleState by rememberSaveable {
                        mutableStateOf(item.active)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    AppearanceToggleButton(toggleState = toggleState, onToggled = { bool ->
                        toggleState = bool
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