package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FilterList
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.*
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.HorizontalPagerIndicator

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeDashBoard(
    bottomSheetState: BottomSheetState,
    currentBottomNavDestinationTitle: String,
    onBackArrowPressed: () -> Unit
) {
    val pagerState = rememberPagerState()
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
                contentDescription = "background"
            )
            Column {
                AnimatedVisibility(visible = visible, enter = slideInVertically {
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                    exit = slideOutVertically(
                        targetOffsetY = { screenHeight }) + shrinkVertically() + fadeOut()
                ) {

                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, end = 24.dp, top = 32.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(color = grey),
                                painter = painterResource(id = R.drawable.ic_profile_pic),
                                contentDescription = "Profile image",
                                alignment = Alignment.TopCenter
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Hi Anayo",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 28.dp, bottom = 16.dp)
                        ) { page: Int ->
                            WalletCard()
                        }
                        HorizontalPagerIndicator(
                            totalDots = 3,
                            selectedIndex = pagerState.currentPage
                        )
                    }
                }

                AnimatedVisibility(visible = !visible, enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                    exit = slideOutVertically(
                        targetOffsetY = { screenHeight }) + shrinkVertically() + fadeOut()
                ) {

                    if (currentBottomNavDestinationTitle != Home.title) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 22.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick = onBackArrowPressed ) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = "Back arrow",
                                    tint = Color.White,
                                )
                            }
                            Text(
                                text = currentBottomNavDestinationTitle,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (currentBottomNavDestinationTitle == Transactions.title) {
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Rounded.FilterList,
                                        contentDescription = "Filter",
                                        tint = Color.White,
                                    )
                                }
                            } else {
                                IconButton(onClick = { }) {}
                            }
                        }
                    } else {
                        Row() {}
                    }
                }
            }
        }
    }
}