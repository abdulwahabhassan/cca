package com.smartflowtech.cupidcustomerapp.ui.presentation.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.SettingsViewModel
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util


@Composable
fun Settings(
    viewModel: SettingsViewModel,
    onLogOutClicked: () -> Unit,
    onEditProfileClicked: () -> Unit,
    onSecurityClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    onPaymentClicked: () -> Unit,
    onBackPressed: () -> Unit,
) {
    var toggleState by rememberSaveable {
        mutableStateOf(true)
    }

    BackHandler(true) {
        onBackPressed()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = Util.getListOfSettingsItems()) { item ->
            Column(
                modifier = Modifier
                    .clickable(enabled = item.name != "Appearance") {
                        when (item.name) {
                            "Edit Profile" -> {
                                onEditProfileClicked()
                            }
                            "Security" -> {
                                onSecurityClicked()
                            }
                            "Notifications" -> {
                                onNotificationClicked()
                            }
                            "Payment" -> {
                                onPaymentClicked()
                            }
                            "Logout" -> {
                                viewModel.logOut()
                                onLogOutClicked()
                            }
                        }
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (item.name != "Logout") {
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = item.icon),
                            contentDescription = "${item.name} icon"
                        )
                    } else {
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color = transparentBlue)
                                .size(32.dp)
                                .padding(6.5.dp),
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "${item.name} icon"
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = item.name,
                            fontFamily = AthleticsFontFamily,
                            fontWeight = FontWeight.W400,
                            color = Color.Black
                        )
                        Text(text = item.description, fontSize = 14.sp, color = grey)
                    }
                    if (item.name == "Appearance") {
                        Spacer(modifier = Modifier.width(16.dp))
                        AppearanceToggleButton(toggleState = toggleState, onToggled = { bool ->
                            toggleState = bool
                        })
                    }
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
fun AppearanceToggleButton(toggleState: Boolean, onToggled: (Boolean) -> Unit) {
    Switch(
        checked = toggleState, onCheckedChange = onToggled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = darkBlue,
            checkedTrackAlpha = 1f,
            uncheckedThumbColor = lightGrey,
            uncheckedTrackColor = grey
        )
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewSettings() {
    CupidCustomerAppTheme {
//        Settings({})
    }

}
