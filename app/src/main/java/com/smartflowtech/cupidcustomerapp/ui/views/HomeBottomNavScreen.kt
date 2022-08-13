package com.smartflowtech.cupidcustomerapp.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R

@Composable
fun Home() {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Home", color = Color.Black)
        //No transaction history column
//        Column(
//            Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.5f),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                modifier = Modifier
//                    .size(80.dp),
//                painter = painterResource(id = R.drawable.ic_no_transactions),
//                contentDescription = "Bottom sheet handle",
//                tint = Color.Unspecified
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(text = "You have no transaction history yet", color = Color.Black)
//        }
    }
}


@Composable
fun Transactions() {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Transactions History", color = Color.Black)
        //No transaction history column
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(80.dp),
                painter = painterResource(id = R.drawable.ic_no_transactions),
                contentDescription = "Bottom sheet handle",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "You have no transaction history yet", color = Color.Black)
        }
    }
}


@Composable
fun Location() {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Location", color = Color.Black)

    }
}


@Composable
fun Settings() {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Settings", color = Color.Black)

    }
}

