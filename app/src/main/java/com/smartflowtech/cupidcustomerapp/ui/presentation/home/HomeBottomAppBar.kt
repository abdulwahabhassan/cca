package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@Composable
fun HomeBottomAppBar(isSelected: (String) -> Boolean, onClicked: (String) -> Unit) {

    val items = listOf(
        com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Home,
        com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Transactions,
//        com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Location,
//        com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Settings,
    )

    BottomAppBar(
        backgroundColor = Color.White,
        modifier = androidx.compose.ui.Modifier
            .navigationBarsPadding()
    ) {

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
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
                onClick = { onClicked(item.route) }
            )
        }
    }
}