package com.smartflowtech.cupidcustomerapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.service.CupidCustomerFirebaseMessagingService
import com.smartflowtech.cupidcustomerapp.ui.presentation.activity.MainActivity
import java.time.LocalDateTime

class NotificationBuilder(
    private val context: Context,
    private val messageTitle: String,
    private val messageBody: String
    ) {

    // Create an explicit intent for an Activity in your app
    private val intent = Intent(
        CupidCustomerFirebaseMessagingService.FIREBASE_MESSAGING_EVENT,
        Uri.EMPTY,
        context,
        MainActivity::class.java
    ).apply {
        putExtra(DATA_PAYLOAD_TITLE_KEY, messageTitle)
        putExtra(DATA_PAYLOAD_BODY_KEY, messageBody)
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    }

    // Create pending intent
    private val showMessageBodyPendingIntent: PendingIntent = PendingIntent
        .getActivity(context, NOTIFICATION_ID, intent, FLAG_UPDATE_CURRENT + FLAG_IMMUTABLE)

    private fun createNotificationBuilder() = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_smartflow)
        .setContentTitle(messageTitle)
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        // Set the intent that will fire when the user taps the notification
        .setContentIntent(showMessageBodyPendingIntent)
        .setAutoCancel(true) // automatically removes the notification when the user taps it.


    //Because you must create the notification channel before posting any notifications on
    //Android 8.0 and higher, you should execute this code as soon as your app starts.
    //It's safe to call this repeatedly because creating an existing notification channel performs
    //no operation.
    private fun createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH

            //initialize a notification channel
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //create channel
            notificationManager.createNotificationChannel(channel)
        }

    }

    fun postNotification() {
        //create notification channel first
        createNotificationChannel()

        //build a notify and post it to the user's status bar by calling
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, createNotificationBuilder().build())
        }
    }

    companion object {
        const val CHANNEL_ID = "PUSH_NOTIFICATION_ID"
        const val NOTIFICATION_ID = 0
        const val DATA_PAYLOAD_TIME_KEY = "time"
        const val DATA_PAYLOAD_BODY_KEY = "body"
        const val DATA_PAYLOAD_TITLE_KEY = "title"
    }

}