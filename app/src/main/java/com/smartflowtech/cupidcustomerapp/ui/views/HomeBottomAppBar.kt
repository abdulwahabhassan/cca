package com.smartflowtech.cupidcustomerapp.ui.views

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import kotlinx.coroutines.launch

@Composable
fun HomeBottomAppBar(isSelected : (String) -> Boolean, onClicked: (String) -> Unit) {

    val items = listOf(
        HomeScreen.Home,
        HomeScreen.Transactions,
        HomeScreen.Location,
        HomeScreen.Settings,
    )
    val navPaddingValues = WindowInsets.navigationBars.asPaddingValues()

    BottomAppBar(
        backgroundColor = Color.White,
        modifier = androidx.compose.ui.Modifier.padding(navPaddingValues)
    ) {

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title, fontSize = 11.sp) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = grey,
                alwaysShowLabel = true,
                selected = isSelected(item.route),
                onClick = { onClicked(item.route) }
            )
        }
    }
}