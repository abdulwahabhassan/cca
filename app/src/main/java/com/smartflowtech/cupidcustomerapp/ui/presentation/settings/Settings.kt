package com.smartflowtech.cupidcustomerapp.ui.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
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
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
fun AppearanceToggleButton(toggleState: Boolean, onToggled: (Boolean) -> Unit) {
    Switch(
        checked = true, onCheckedChange = onToggled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = darkBlue,
            uncheckedThumbColor = lightGrey,
            uncheckedTrackColor = grey
        )
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewSettingsItem() {
    CupidCustomerAppTheme {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = Util.getListOfSettingsItems()) { item ->
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                        if (item.name == "Appearance") {
                            var toggleState by rememberSaveable {
                                mutableStateOf(true)
                            }
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

}
