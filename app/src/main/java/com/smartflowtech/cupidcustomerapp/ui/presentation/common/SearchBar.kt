package com.smartflowtech.cupidcustomerapp.ui.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.AdsCard
import com.smartflowtech.cupidcustomerapp.ui.theme.*

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchBarClicked: () -> Unit = {},
    searchPlaceholder: String,
    applyBorder: Boolean = false,
    maxWidthFraction: Float = 1f
) {

    TextField(
        modifier = if (applyBorder)
            Modifier
                .fillMaxWidth(maxWidthFraction)
                .height(50.dp)
                .padding(horizontal = 16.dp)
                .border(1.dp, lineGrey, RoundedCornerShape(12.dp))
        else
            Modifier
                .fillMaxWidth(maxWidthFraction)
                .height(50.dp)
                .padding(horizontal = 16.dp),
        value = query,
        onValueChange = { text ->
            onQueryChange(text)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search icon",
                modifier = Modifier.size(24.dp)
            )
        },
        singleLine = true,
        placeholder = {
            Text(
                text = searchPlaceholder,
                fontFamily = AthleticsFontFamily,
                color = grey,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (applyBorder) Color.White else lightGrey,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Press) {
                            onSearchBarClicked()
                        }
                    }
                }
            }
    )
}

@Composable
@Preview(showBackground = true)
fun SearchBarPreview() {
    SearchBar("", {}, {}, "Search transactions")
}