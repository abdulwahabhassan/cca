package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen
import com.smartflowtech.cupidcustomerapp.ui.theme.NoRippleInteractionSource
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@Composable
fun HomeBottomAppBar(
    isSelected: (String) -> Boolean,
    onClicked: (String) -> Unit,
    visible: Boolean
) {

    val items = listOf(
        HomeScreen.Home,
        HomeScreen.Transactions,
        HomeScreen.Location,
        HomeScreen.Settings,
        HomeScreen.Profile
    )

    if (visible) {
        BottomAppBar(
            backgroundColor = Color.White,
            modifier = Modifier
                .navigationBarsPadding()
        ) {
            items.forEach { item ->
                if (item.route != HomeScreen.Profile.route) {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = if (item.title == "Transactions") "History" else item.title,
                                fontSize = 11.sp
                            )
                        },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = grey,
                        alwaysShowLabel = LocalConfiguration.current.screenWidthDp.dp > 320.dp,
                        selected = isSelected(item.route),
                        onClick = { onClicked(item.route) },
                        interactionSource = NoRippleInteractionSource()
                    )
                }
            }
        }
    } else {
        Spacer(modifier = Modifier.navigationBarsPadding().height(56.dp))
    }
}