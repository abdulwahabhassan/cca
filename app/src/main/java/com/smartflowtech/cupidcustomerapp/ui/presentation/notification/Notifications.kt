package com.smartflowtech.cupidcustomerapp.ui.presentation.notification

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.NotificationItem
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Header
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.NotificationsViewModel
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.lightGrey
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun Notifications(
    onBackPressed: () -> Unit,
    uiState: NotificationsScreenUiState,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {

    BackHandler(true) {
        onBackPressed()
    }

    when (uiState.viewModelResult) {
        ViewModelResult.LOADING -> {
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(strokeWidth = 2.dp)
            }
        }
        ViewModelResult.ERROR -> {
            Column(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_no_data),
                    contentDescription = "No notifications icon",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "There are no notifications")
            }

            LaunchedEffect(key1 = Unit) {
                bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    uiState.message ?: "Oops! Something went wrong!"
                )
            }
        }
        ViewModelResult.SUCCESS -> {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {

                val newNotifications =
                    uiState.notifications.filter {
                        LocalDateTime.parse(
                            it.createdAt,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        ).toLocalDate() == LocalDateTime.now().toLocalDate()
                    }
                val olderNotifications =
                    uiState.notifications.filter {
                        LocalDateTime.parse(
                            it.createdAt,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        ).toLocalDate() != LocalDateTime.now().toLocalDate()
                    }
                val notifications = mapOf(
                    "New" to newNotifications,
                    "Older" to olderNotifications
                )

                Spacer(modifier = Modifier.height(40.dp))

                if (notifications.values.flatten().isEmpty()) {
                    Column(
                        Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp),
                            painter = painterResource(id = R.drawable.ic_no_data),
                            contentDescription = "No notifications icon",
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "There are no notifications")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        userScrollEnabled = true,
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        notifications.forEach { (header, notifications) ->
                            if (notifications.isNotEmpty()) {
                                stickyHeader {
                                    Header(
                                        header,
                                        bgColor = lightGrey,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                items(notifications) { item ->
                                    Notification(item)
                                }
                            }
                        }
                    }
                }
            }
        }

    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun PreviewNotificationsList() {
    CupidCustomerAppTheme {
        Notifications(
            onBackPressed = {},
            uiState = NotificationsScreenUiState(
                ViewModelResult.LOADING,
                notifications = emptyList()
            ),
            bottomSheetScaffoldState = BottomSheetScaffoldState(
                drawerState = DrawerState(
                    DrawerValue.Closed
                ),
                bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
                snackbarHostState = SnackbarHostState()
            )
        )
    }
}
