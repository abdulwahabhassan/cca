package com.smartflowtech.cupidcustomerapp

import android.app.Application
import co.paystack.android.PaystackSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CupidCustomerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimberLog()
        PaystackSdk.initialize(applicationContext);
        PaystackSdk.setPublicKey(BuildConfig.PSTK_PUBLIC_LIVE_KEY)
    }

    private fun initTimberLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}