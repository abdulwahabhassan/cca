package com.smartflowtech.cupidcustomerapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    goTo: () -> Unit,
    isNavDestinationSelected: (String) -> Boolean,
    onBottomNavItemClicked: (String) -> Unit
) {

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    Scaffold(
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                HomeBottomAppBar(
                    isSelected = isNavDestinationSelected,
                    onClicked = onBottomNavItemClicked.also { route ->
                        coroutineScope.launch {
                            if (navHostController.currentDestination?.route == HomeScreen.Home.route) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues: PaddingValues ->

        BottomSheetScaffold(
            modifier = Modifier.padding(paddingValues = paddingValues),
            scaffoldState = bottomSheetScaffoldState,
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent,
            sheetPeekHeight = LocalConfiguration.current.screenHeightDp.dp * 0.54f,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetContent = {
                Spacer(
                    modifier = Modifier
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                        .fillMaxWidth()
                )
                Column(
                    Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        )
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(40.dp),
                        painter = painterResource(id = R.drawable.ic_bottom_sheet_handle_inactive),
                        contentDescription = "Bottom sheet handle",
                        tint = Color.Unspecified
                    )
                    HomeScreenBottomNavBarNavigation(bottomNavHostController = navHostController)
                }
            }) { paddingValues ->
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
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 32.dp),
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
                                .padding(top = 32.dp, bottom = 16.dp)
                        ) { page: Int ->
                            Card(
                                modifier = Modifier.fillMaxWidth(0.92f),
                                shape = RoundedCornerShape(10.dp),
                                backgroundColor = skyBlue
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_card_decoration),
                                            contentDescription = "Card decoration",
                                            tint = Color.Unspecified
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, end = 16.dp, bottom = 28.dp)
                                    ) {
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column {
                                                Row {
                                                    Text(
                                                        text = "Wallet Balance",
                                                        color = Color.Black
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Icon(
                                                        imageVector = Icons.Outlined.VisibilityOff,
                                                        contentDescription = "Balance Visibility"
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "₦0.00",
                                                    color = Color.Black,
                                                    style = MaterialTheme.typography.h6
                                                )
                                            }
                                            GradientButton(
                                                text = "+Add funds",
                                                gradient = Brush.horizontalGradient(
                                                    gradientBluePurple
                                                ),
                                                modifier = Modifier
                                                    .wrapContentWidth()
                                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(32.dp))
                                        Divider(
                                            thickness = 1.dp,
                                            color = lineGrey,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.height(24.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = "Card Number VLX-90345", fontSize = 12.sp)
                                            Text(
                                                text = "Loyalty Points: 406",
                                                fontSize = 12.sp,
                                                color = blue
                                            )
                                        }

                                    }
                                }
                            }
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CupidCustomerAppTheme {
        //HomeScreen({}, {}, { true }, {})
    }
}