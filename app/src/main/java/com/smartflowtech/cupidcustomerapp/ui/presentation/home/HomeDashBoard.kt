package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.HorizontalPagerIndicator
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeDashBoard(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onAddFundsClicked: () -> Unit,
    fullName: String,
    walletBalanceVisibility: Boolean,
    updateWalletVisibility: (Boolean) -> Unit,
    homeScreenUiState: HomeScreenUiState,
    onCardSelected: (Boolean, String) -> Unit,
    isCardSelected: Boolean,
    onProfileClicked: () -> Unit,
    onNotificationsClicked: () -> Unit,
    profilePicture: String,
    getTransactions: () -> Unit,
    logOut: () -> Unit
) {

    val pagerState = rememberPagerState()
    var visible by rememberSaveable { mutableStateOf(true) }
    visible =
        bottomSheetScaffoldState.bottomSheetState.direction == 0f &&
                bottomSheetScaffoldState.bottomSheetState.isCollapsed
    var showSnackBar by rememberSaveable { mutableStateOf(false) }
    var showLoadingIndicator by rememberSaveable { mutableStateOf(false) }
    var showWallets by rememberSaveable { mutableStateOf(false) }
    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    when (homeScreenUiState.viewModelResult) {
        ViewModelResult.ERROR -> {
            showLoadingIndicator = false
            showWallets = false
            showSnackBar = true
            if (homeScreenUiState.message?.contains("Token Expired", true) == true) {
                logOut()
                LaunchedEffect(key1 = Unit) {
                    Toast.makeText(ctx, "Session Expired! Log in again!", Toast.LENGTH_LONG).show()
                }
            }
        }
        ViewModelResult.LOADING -> {
            showWallets = false
            showSnackBar = false
            showLoadingIndicator = true
        }
        ViewModelResult.SUCCESS -> {
            //since we may have success response that holds cached data,
            //users still needs to know when they are not connected to the internet
            if (homeScreenUiState.message?.contains(
                    "Check your internet connection!",
                    true
                ) == true
            ) {
                showLoadingIndicator = false
                showSnackBar = true
                showWallets = false
            } else {
                showLoadingIndicator = false
                showSnackBar = false
                showWallets = true
            }


        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top
    ) {

        Box(contentAlignment = Alignment.TopCenter) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.ic_design_background),
                contentDescription = "background"
            )
            Column(modifier = Modifier) {
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically() { it / 1000 } + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it / 1000 }) + fadeOut()
                ) {
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
                                        onCardSelected(false, "")
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


                                Image(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .clipToBounds()
                                        .clickable {
                                            onProfileClicked()
                                        },
                                    painter = if (profilePicture.isEmpty())
                                        painterResource(id = R.drawable.ic_avatar)
                                    else
                                        rememberAsyncImagePainter(model = profilePicture),
                                    contentDescription = "Avatar",
                                    contentScale = ContentScale.Crop
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
                                            text = "Hi ${
                                                fullName.substringBefore(" ").replaceFirstChar {
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
                                        modifier = Modifier
                                            .padding(bottom = 16.dp)
                                            .clip(RoundedCornerShape(50))
                                            .clipToBounds()
                                            .clickable {
                                                onNotificationsClicked()
                                            }
                                            .padding(8.dp),
                                        painter = painterResource(id = R.drawable.ic_notification_active),
                                        contentDescription = "Notification bell",
                                        tint = Color.White,
                                    )

                                }
                            }
                        }

                        HorizontalPager(
                            count = if (
                                homeScreenUiState.viewModelResult ==
                                ViewModelResult.SUCCESS &&
                                homeScreenUiState.wallets.isNotEmpty()
                            )
                                homeScreenUiState.wallets.size else 1,
                            state = pagerState,
                            itemSpacing = 16.dp,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            userScrollEnabled = !isCardSelected,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(LocalConfiguration.current.screenHeightDp.dp * 0.30f)
                                .padding(top = if (isCardSelected) 8.dp else 16.dp, bottom = 16.dp)
                        ) { page: Int ->
                            if (showLoadingIndicator) {
                                CircularProgressIndicator(color = pink, strokeWidth = 2.dp)
                            } else {
                                if (showWallets) {
                                    if (homeScreenUiState.wallets.isNotEmpty()) {
                                        WalletCard(
                                            listOf(lightPink, lightYellow, skyBlue).random(),
                                            onAddFundsClicked = onAddFundsClicked,
                                            walletBalanceVisibility = walletBalanceVisibility,
                                            updateWalletVisibility = updateWalletVisibility,
                                            vendorName = homeScreenUiState.wallets[page].vendorName
                                                ?: "",
                                            currentBalance = homeScreenUiState.wallets[page].currentBalance
                                                ?: "0.00",
                                            nfcTagCode = homeScreenUiState.wallets[page].nfcTagCode
                                                ?: "",
                                            onCardSelected = { tagCode ->
                                                if (!isCardSelected) {
                                                    onCardSelected(true, tagCode)
                                                }
                                            },
                                            isCardSelected
                                        )
                                    } else {
                                        Text(
                                            text = "No cards found",
                                            color = purple,
                                            fontSize = 14.sp,
                                            modifier = Modifier
                                                .background(
                                                    color = transparentPurple,
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                                .padding(vertical = 8.dp, horizontal = 12.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else if (showSnackBar) {
                                    if (homeScreenUiState.message?.isNotEmpty() == true) {
                                        LaunchedEffect(
                                            key1 = Unit,
                                            block = {
                                                val result =
                                                    bottomSheetScaffoldState
                                                        .snackbarHostState
                                                        .showSnackbar(
                                                            message = homeScreenUiState.message,
                                                            actionLabel = "Retry",
                                                            duration = SnackbarDuration.Indefinite
                                                        )
                                                if (result == SnackbarResult.ActionPerformed) {
                                                    getTransactions()
                                                }
                                            }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun PreviewHomeDashBoard() {
    var visible by remember { mutableStateOf(true) }
    var isCardSelected by remember { mutableStateOf(false) }
    CupidCustomerAppTheme {
        HomeDashBoard(
            bottomSheetScaffoldState = BottomSheetScaffoldState(
                drawerState = DrawerState(
                    DrawerValue.Closed
                ),
                bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
                snackbarHostState = SnackbarHostState()
            ),
            onAddFundsClicked = { },
            fullName = "Mike Murdock",
            walletBalanceVisibility = visible,
            updateWalletVisibility = {
                visible = !it
            },
            homeScreenUiState = HomeScreenUiState(
                ViewModelResult.SUCCESS,
                Util.getListOfTransactions(),
                "",
                Util.getListsOfWallets()
            ),
            onCardSelected = { bool, _ ->
                isCardSelected = !bool
            },
            isCardSelected = isCardSelected,
            onProfileClicked = {},
            onNotificationsClicked = {},
            profilePicture = "",
            getTransactions = { },
            logOut = { }
        )
    }
}