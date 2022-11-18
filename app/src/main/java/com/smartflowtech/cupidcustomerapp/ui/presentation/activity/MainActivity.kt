package com.smartflowtech.cupidcustomerapp.ui.presentation.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailabilityLight
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.service.CupidCustomerFirebaseMessagingService
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.RootNavigation
import com.smartflowtech.cupidcustomerapp.ui.presentation.notification.NotificationBuilder.Companion.DATA_PAYLOAD_BODY_KEY
import com.smartflowtech.cupidcustomerapp.ui.presentation.notification.NotificationBuilder.Companion.DATA_PAYLOAD_TITLE_KEY
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.MainActivityViewModel
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var activityViewModel: MainActivityViewModel

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications
            initFCMPushNotification()
        } else {
            //Inform user that that your app will not show notifications
            AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setTitle("Push Notification Declined")
                .setMessage("You will not be able to receive notifications unless granted")
                .setNegativeButton("Dismiss") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
                initFCMPushNotification()
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                AlertDialog.Builder(this,  R.style.AlertDialogStyle)
                    .setTitle("Enable Notifications")
                    .setMessage(
                        "Notifications allow us to be able to deliver urgent information " +
                                "about activities on your account and newly added features that you" +
                                "would like to explore. Kindly allow post notification permission."
                    )
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    .setNegativeButton("No, Thanks") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setCancelable(false)
                    .show()
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            initFCMPushNotification()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            CupidCustomerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    RootNavigation(
                        finishActivity = { finishActivity() }
                    )

                    askNotificationPermission()

                    //PROCESS NOTIFICATION DATA OF FCM NOTIFICATION RECEIVED IN BACKGROUND
                    val title = intent.extras?.getString(DATA_PAYLOAD_TITLE_KEY)
                    val body = intent.extras?.getString(DATA_PAYLOAD_BODY_KEY)

                    Timber.d("OnCreate -> payload title: $title payload body: $body")

                    if (title != null && body != null) {
                        showNotificationDialog(title, body)
                    }

                }
            }
        }
    }

    private fun initFCMPushNotification() {
        //if app has Google play services installed, go ahead and retrieve token
        if (checkGooglePlayServices()) {
            Timber.d("Device has google play services")
            //retrieve token
            getFCMRegistrationToken()
            //On initial startup of your app, the FCM SDK generates a registration token for
            //the client app instance.
            //you'll need to access this token by extending FirebaseMessagingService and
            //overriding onNewToken.
        } else {
            //You won't be able to send notifications to this device
            Timber.d("Device doesn't have google play services")

        }
    }

    public override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Timber.d("New Intent: $intent")
        resolveIntent(intent)
    }

    private fun resolveIntent(intent: Intent) {
        lifecycleScope.launchWhenResumed {
            if (activityViewModel.appConfigPreferences.loggedIn) {
                when (intent.action) {
                    CupidCustomerFirebaseMessagingService.FIREBASE_MESSAGING_EVENT -> {
                        val title = intent.extras?.getString(DATA_PAYLOAD_TITLE_KEY)
                        val body = intent.extras?.getString(DATA_PAYLOAD_BODY_KEY)

                        //PROCESS NOTIFICATION DATA OF FCM NOTIFICATION RECEIVED IN FOREGROUND
                        Timber.d(
                            "OnNewIntent (FIREBASE_MESSAGING_EVENT) -> " +
                                    "payload title: $title payload body: $body",
                        )

                        if (title != null && body != null) {
                            showNotificationDialog(title, body)
                        }
                    }
                }
            }
        }
    }

    private fun showNotificationDialog(title: String, body: String) {
        AlertDialog.Builder(this, R.style.AlertDialogStyle)
            .setTitle(title)
            .setMessage(body.capitalizeFirstLetter())
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .setIcon(R.drawable.ic_smartflow)
            .show()
    }

    private fun checkGooglePlayServices(): Boolean {
        val status = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(this)
        return if (status != ConnectionResult.SUCCESS) {
            Timber.d("Error")
            //Ask user to update google play services and manage the error.
            false
        } else {
            Timber.d("Google play services updated")
            true
        }

    }

    private fun getFCMRegistrationToken() {
        //Because the token could be rotated after initial startup, you are strongly recommended
        //to retrieve the latest updated registration token.

        //To retrieve the registration token for the client app instance
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.d("Fetching FCM registration token failed: ${task.exception}")
                return@OnCompleteListener
            }

            //Get new FCM registration token if task was successful
            //This action may have already been performed in onNewToken()
            //in FirebaseMessagingService, nevertheless
            val token = task.result
            activityViewModel.addDeviceToken(fcmToken = token)

            //The device token is a unique identifier that contains two things:
            //- Which device will receive the notification.
            //- The app within that device that will receive the notification.

            //Retrieve token as a String, Log and toast it
            val msg = getString(R.string.msg_token_fmt, token)
            Timber.d(msg)

            //When the device token is retrieved, Firebase can now connect with the device
        })

    }

    private fun finishActivity() {
        finish()
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CupidCustomerAppTheme {

    }
}