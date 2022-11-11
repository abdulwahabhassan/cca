package com.smartflowtech.cupidcustomerapp.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.NotificationsRepository
import com.smartflowtech.cupidcustomerapp.model.request.UpdateDeviceTokenRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.utils.NotificationBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CupidCustomerFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationsRepository: NotificationsRepository

    @Inject
    lateinit var prefsRepository: DataStorePrefsRepository

    @Inject
    lateinit var dispatcher: CoroutineDispatcher

    //Called if the FCM registration token is updated. This may occur if the security of
    //the previous token had been compromised. Note that this is called when the
    //FCM registration token is initially generated so this is where you would retrieve the token.
    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")
        //we could save to database
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendFCMTokenToServer(token)
    }

    private fun sendFCMTokenToServer(token: String) {
        CoroutineScope(dispatcher).launch {
            val result = notificationsRepository.updateDeviceToken(
                token = token,
                updateDeviceTokenRequestBody = UpdateDeviceTokenRequestBody(
                    prefsRepository.getAppConfigPreferences().userId,
                    deviceToken = token
                )
            )
            when (result) {
                is RepositoryResult.Error -> {
                    Timber.d("$result")
                }

                is RepositoryResult.Success -> {
                    Timber.d("$result")
                }
            }
        }
    }

    //When receiving notifications, it’s important to distinguish whether the app is running on the
    //foreground or background. Basically, you need to consider the following:
    //-Foreground: The notification and the data are both handled in onMessageReceived()
    //-Background: The System UI handles the notification.
    override fun onMessageReceived(p0: RemoteMessage) {
        Timber.d("on message received called")
        super.onMessageReceived(p0)
        //handle the message received
        handleMessage(p0)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        Timber.d("handle message called")
        Timber.d("remote message data: ${remoteMessage.data}")

        val title = remoteMessage.data["title"]
        val body = remoteMessage.data["body"]

        Timber.d("handleMessage (OnMessageReceived) -> $title $body")

        //post notification
        NotificationBuilder(
            context = this,
            messageTitle = title ?: "",
            messageBody = body ?: ""
        ).postNotification()
    }

    companion object {
        const val FIREBASE_MESSAGING_EVENT = "com.google.firebase.MESSAGING_EVENT"
    }

}