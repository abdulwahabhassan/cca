package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.snap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.HorizontalPagerIndicator
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.HomeScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import java.util.*

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeDashBoard(
    bottomSheetState: BottomSheetState,
    onAddFundsClicked: () -> Unit,
    userName: String,
    walletBalanceVisibility: Boolean,
    updateWalletVisibility: (Boolean) -> Unit,
    homeScreenUiState: HomeScreenUiState,
    onLogOutClicked: () -> Unit,
    onCardSelected: (Boolean) -> Unit,
    isCardSelected: Boolean
) {
    val pagerState = rememberPagerState()
    val ctx = LocalContext.current
    var visible by remember { mutableStateOf(true) }
    visible =
        bottomSheetState.direction == 0f && bottomSheetState.isCollapsed

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
            Column(modifier = Modifier) {
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically() { it / 1000 } + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it / 1000 }) + fadeOut()) {
                    Column() {
                        Spacer(
                            modifier = Modifier
                                .height(12.dp)
                        )
                        if (isCardSelected) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(
                                    onClick = {
                                        onCardSelected(false)
                                    }) {
                                    Icon(
                                        imageVector = Icons.Rounded.ArrowBack,
                                        contentDescription = "Back arrow",
                                        tint = Color.White,
                                    )
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 28.dp,
                                        bottom = 8.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(
                                    onClick = { }, modifier = Modifier
                                        .size(50.dp)
                                        .background(color = transparentBlue, CircleShape)
                                ) {
                                    Text(
                                        text = userName.first().toString() + userName.substring(
                                            1,
                                            2
                                        ),
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
//                                Icon(
//                                    painter = painterResource(id = R.drawable.ic_notification_active),
//                                    contentDescription = "Notification bell",
//                                    tint = Color.White,
//                                )
                                    IconButton(onClick = { onLogOutClicked() }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Logout,
                                            contentDescription = "Logout icon",
                                            tint = Color.White,
                                        )
                                    }
                                }
                            }
                        }

                        HorizontalPager(
                            count = if (homeScreenUiState.viewModelResult == ViewModelResult.SUCCESS) homeScreenUiState.wallets.size else 1,
                            state = pagerState,
                            itemSpacing = 16.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            userScrollEnabled = !isCardSelected,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(LocalConfiguration.current.screenHeightDp.dp * 0.30f)
                                .padding(top = if (isCardSelected) 8.dp else 16.dp, bottom = 16.dp)
                        ) { page: Int ->
                            when (homeScreenUiState.viewModelResult) {
                                ViewModelResult.ERROR -> {}
                                ViewModelResult.LOADING -> {
                                    CircularProgressIndicator(color = pink, strokeWidth = 2.dp)
                                }
                                ViewModelResult.INITIAL -> {}
                                ViewModelResult.SUCCESS -> {
                                    if (homeScreenUiState.wallets.isNotEmpty()) {
                                        WalletCard(
                                            listOf(lightPink, lightYellow, skyBlue).random(),
                                            onAddFundsClicked = onAddFundsClicked,
                                            walletBalanceVisibility = walletBalanceVisibility,
                                            updateWalletVisibility = updateWalletVisibility,
                                            vendorName = homeScreenUiState.wallets[page].vendorName,
                                            currentBalance = homeScreenUiState.wallets[page].currentBalance,
                                            onCardSelected = { cardId ->
                                                if (!isCardSelected) {
                                                    onCardSelected(true)
                                                }
                                            },
                                            isCardSelected
                                        )
                                    } else {
                                        Text(
                                            "No cards found",
                                            color = purple,
                                            fontSize = 12.sp,
                                            modifier = Modifier
                                                .background(
                                                    color = transparentPurple,
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                                .padding(vertical = 8.dp, horizontal = 12.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalPagerIndicator(
                            totalDots = homeScreenUiState.wallets.size,
                            selectedIndex = pagerState.currentPage,
                            isVisible = !isCardSelected
                        )
                    }
                }


            }
        }
    }
}