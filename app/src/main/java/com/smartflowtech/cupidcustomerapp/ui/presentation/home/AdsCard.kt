package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.pink
import com.smartflowtech.cupidcustomerapp.ui.theme.skyBlue

@Composable
fun AdsCard(page: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = if (page == 0) pink else skyBlue
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                .height(120.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Get 30% on your first transaction",
                color = darkBlue,
               overflow = TextOverflow.Clip,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Bottom),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_gift_box),
                contentDescription = "Gift box"
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AdsCardPreview() {
    AdsCard(page = 1)
}