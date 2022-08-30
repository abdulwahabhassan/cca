package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.HorizontalPagerIndicator
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import java.util.*

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeDashBoard(
    horizontalPagerHeight: Dp,
    bottomSheetState: BottomSheetState,
    onAddFundsClicked: () -> Unit,
    userName: String,
    walletBalanceVisibility: Boolean,
    updateWalletVisibility: (Boolean) -> Unit
) {
    val pagerState = rememberPagerState()

    //Animation params
    var visible by remember { mutableStateOf(true) }
    visible =
        bottomSheetState.direction >= 0f && bottomSheetState.currentValue == BottomSheetValue.Collapsed
    val density = LocalDensity.current
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top
    ) {

        Box(contentAlignment = Alignment.TopCenter) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_design_background),
                contentDescription = "background",
                contentScale = ContentScale.FillBounds
            )
            Column {
                AnimatedVisibility(visible = visible, enter = slideInVertically() {
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                    exit = slideOutVertically(
                        targetOffsetY = { screenHeight }) + shrinkVertically() + fadeOut()
                ) {

                    Column(
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 32.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(
                                onClick = { }, modifier = Modifier
                                    .size(50.dp)
                                    .background(color = transparentBlue, CircleShape)
                            ) {
                                Text(
                                    text = userName.first().toString() + userName.substring(1, 2),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Hi ${
                                            userName.replaceFirstChar {
                                                if (it.isLowerCase()) it.titlecase(
                                                    Locale.getDefault()
                                                ) else it.toString()
                                            }
                                        }",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(text = "Trust you are good", color = Color.White)
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_notification_active),
                                    contentDescription = "Notification bell",
                                    tint = Color.White,
                                )
                            }
                        }
                        HorizontalPager(
                            count = 3,
                            state = pagerState,
                            itemSpacing = 16.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(horizontalPagerHeight)
                                .padding(top = 16.dp, bottom = 16.dp)
                        ) { page: Int ->
                            WalletCard(
                                listOf(lightPink, lightYellow, skyBlue)[page],
                                onAddFundsClicked = onAddFundsClicked,
                                walletBalanceVisibility = walletBalanceVisibility,
                                updateWalletVisibility = updateWalletVisibility
                            )
                        }
                        HorizontalPagerIndicator(
                            totalDots = 3,
                            selectedIndex = pagerState.currentPage
                        )
                    }
                }

            }
        }
    }
}