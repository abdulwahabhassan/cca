package com.smartflowtech.cupidcustomerapp.ui.utils

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber
import java.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object Util {
    fun formatAmount(value: Any): String {
        val valueToBeFormatted: Number = if (value is String) {
            value.toDouble()
        } else {
            value as Number
        }

        val df = DecimalFormat("##,###,##0.00")
        return df.format(valueToBeFormatted)
    }

    fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        sdf.timeZone = TimeZone.getTimeZone("Africa/Lagos")
        val now = Date()
        return sdf.format(now)
    }

    fun formatDateTimeToFullDate(dateTime: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        val formatter = SimpleDateFormat("E, dd MMM yyyy", Locale.UK)
        formatter.timeZone = TimeZone.getTimeZone("Africa/Lagos")
        return formatter.format(parser.parse(dateTime) ?: "")
    }

    fun <T> loadJsonFromAsset(context: Context, fileName: String): T? {
        try {
            val stream = context.assets.open(fileName)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val stringJson = String(buffer, charset("UTF-8"))
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val listType = object : TypeToken<T>() {}.type
            val adapter: JsonAdapter<T> = moshi.adapter(listType)
            val data = adapter.fromJson(stringJson)
            Timber.d("Data $data")
            return data
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }

    fun getListOfBanks(): List<Bank> {
        return listOf(
            Bank(
                id = "1",
                name = "Access Bank",
                icon = R.drawable.ic_access,
                code = "044"
            ),
            Bank(
                id = "2",
                name = "Citibank",
                icon = R.drawable.ic_citi,
                code = "023"
            ),
            Bank(
                id = "3",
                name = "Diamond Bank",
                icon = R.drawable.ic_diamond,
                code = "063"
            ),
            Bank(
                id = "5",
                name = "Ecobank Nigeria",
                icon = R.drawable.ic_eco,
                code = "050"
            ),
            Bank(
                id = "6",
                name = "Fidelity Bank Nigeria",
                icon = R.drawable.ic_fidelity,
                code = "070"
            ), Bank(
                id = "7",
                name = "First Bank of Nigeria",
                icon = R.drawable.ic_first_bank,
                code = "011"
            ),
            Bank(
                id = "8",
                name = "First City Monument Bank",
                icon = R.drawable.ic_fcmb,
                code = "214"
            ),
            Bank(
                id = "9",
                name = "Guaranty Trust Bank",
                icon = R.drawable.ic_gtb,
                code = "058"
            ),
            Bank(
                id = "10",
                name = "Heritage Bank Plc",
                icon = R.drawable.ic_hb,
                code = "030"
            ),
            Bank(
                id = "11",
                name = "Jaiz Bank",
                icon = R.drawable.ic_jaiz,
                code = "301"
            ),
            Bank(
                id = "12",
                name = "Keystone Bank Limited",
                icon = R.drawable.ic_keystone,
                code = "082"
            ),
            Bank(
                id = "13",
                name = "Providus Bank Plc",
                icon = R.drawable.ic_providus,
                code = "101"
            ),
            Bank(
                id = "14",
                name = "Polaris Bank",
                icon = R.drawable.ic_polaris,
                code = "076"
            ), Bank(
                id = "15",
                name = "Stanbic IBTC Bank Nigeria Limited",
                icon = R.drawable.ic_stanbic,
                code = "221"
            ),
            Bank(
                id = "16",
                name = "Standard Chartered Bank",
                icon = R.drawable.ic_standard_chartered,
                code = "068"
            ),
            Bank(
                id = "17",
                name = "Sterling Bank",
                icon = R.drawable.ic_sterling,
                code = "232"
            ), Bank(
                id = "18",
                name = "Suntrust Bank Nigeria Limited",
                icon = R.drawable.ic_suntrust,
                code = "100"
            ),
            Bank(
                id = "19",
                name = "Union Bank of Nigeria",
                icon = R.drawable.ic_union,
                code = "032"
            ),
            Bank(
                id = "20",
                name = "United Bank for Africa",
                icon = R.drawable.ic_uba,
                code = "033"
            ),
            Bank(
                id = "21",
                name = "Unity Bank Plc",
                icon = R.drawable.ic_unity,
                code = "215"
            ),
            Bank(
                id = "22",
                name = "Wema Bank",
                icon = R.drawable.ic_wema,
                code = "035"
            ),
            Bank(
                id = "23",
                name = "Zenith Bank",
                icon = R.drawable.ic_zenith,
                code = "057"
            )
        )
    }

    fun getListOfSettingsItems(): List<SettingsItem> {
        return listOf(
            SettingsItem(
                "Edit Profile",
                "Change your profile information",
                R.drawable.ic_profile
            ),
            SettingsItem(
                "Security",
                "Change password",
                R.drawable.ic_security
            ),
            SettingsItem(
                "Notifications",
                "Control notification preferences",
                R.drawable.ic_notification
            ),
            SettingsItem(
                "Payment",
                "Choose default payment method",
                R.drawable.ic_payment
            ),
            SettingsItem(
                "Appearance",
                "Switch between light and dark theme",
                R.drawable.ic_appearance
            )
        )
    }

    fun getListOfNotificationSettingsItems(): List<NotificationSettingsItem> {
        return listOf(
            NotificationSettingsItem(
                "Email Notifications",
                "Manage your preferences",
                R.drawable.ic_email,
                false
            ),
            NotificationSettingsItem(
                "Push Notifications",
                "Manage your preferences",
                R.drawable.ic_push_notification,
                true
            ),

            )
    }

    fun getListOfNotifications(): List<NotificationItem> {
        return listOf(
            NotificationItem(
                "1",
                "Your wallet was topped up with ₦600",
                "2022-10-21T00:00:00",
                R.drawable.ic_debit_notification
            ),
            NotificationItem(
                "2",
                "₦1,000 was debited from your wallet",
                "2022-10-21T08:00:00",
                R.drawable.ic_credit_notification
            ),
            NotificationItem(
                "3",
                "Get 10% off any payment you make today",
                "2022-10-15T10:20:30",
                R.drawable.ic_gift_notification
            ),
            NotificationItem(
                "4",
                "Your wallet was topped up with ₦5,000",
                "2022-10-09T09:45:00",
                R.drawable.ic_credit_notification
            ),
            NotificationItem(
                "5",
                "You have received 40 loyalty points",
                "2022-10-01T13:00:00",
                R.drawable.ic_gift_notification
            )

        )
    }

    fun getListOfUploadImageOptions(): List<UploadImageOption> {
        return listOf(
            UploadImageOption(
                "Choose from gallery",
                "Select a picture from gallery",
                R.drawable.ic_gallery
            ),
            UploadImageOption(
                "Take a photo",
                "Take a live selfie of your self",
                R.drawable.ic_photo
            )

        )
    }

    fun getListOfDaysFilter(): List<DaysFilter> {
        return listOf(
            DaysFilter.TODAY,
            DaysFilter.LAST_SEVEN_DAYS,
            DaysFilter.LAST_THIRTY_DAYS
        )
    }

    fun getListOfStationsFilter(): List<String> {
        return listOf(
            "States",
            "LGA"
        )
    }

    fun getListOfStations(): List<Station> {
        return listOf(
            Station(
                "Ardova Gas Station",
                "10, Koka Road, Iyana Ipaja",
                "ardova@gmail.com",
                "08167039661"
            ),
            Station(
                "Orlando Petrol",
                "21, Allen junction, Ikeja, Lagos",
                "orlandopetrol@gmail.com",
                "09039393918"
            ),
            Station(
                "Petrocam",
                "Block 50B, Festac",
                "petrocam@gmail.com",
                "07038271927"
            ),
            Station(
                "Mobil Gas Station",
                "Ijesha",
                "mobilgasstation@gmail.com",
                ""
            )
        )
    }

    fun getListOfTransactions(): List<Transaction> {
        return listOf(
            Transaction(
                "Completed",
                "12:24AM",
                "Transaction",
                "₦67,000.00",
                date = "2022-08-01",
                authType = "Credit",
                transactionSeqNumber = "TRS90399291",
                vendorStationName = "Purchase on Cupid",
                product = "PMS",
                nfcTagCode = "123"
            ),
            Transaction(
                "Pending",
                "11:24PM",
                "Transaction",
                "₦30,000.00",
                date = "2022-08-15",
                authType = "Credit",
                transactionSeqNumber = "TRS765599291",
                vendorStationName = "Wallet Top-Up",
                product = "PMS",
                nfcTagCode = "456"
            ),
            Transaction(
                "Completed",
                "11:20AM",
                "Transaction",
                "₦12,000.00",
                date = "2022-08-30",
                authType = "Credit",
                transactionSeqNumber = "TRS765599291",
                vendorStationName = "Wallet Top-Up",
                product = "AGO",
                nfcTagCode = "123"
            ),
            Transaction(
                "Failed",
                "01:24PM",
                "Transaction",
                "₦17,000.00",
                date = "2022-07-01",
                authType = "Debit",
                transactionSeqNumber = "TRS723599291",
                vendorStationName = "Mobile Transfer",
                product = "AGO",
                nfcTagCode = "123"
            ),
            Transaction(
                "Completed",
                "11:24PM",
                "Transaction",
                "₦65,000.00",
                date = "2022-07-15",
                authType = "Debit",
                transactionSeqNumber = "TRS0949482392",
                vendorStationName = "Mobile Transfer",
                product = "DPK",
                nfcTagCode = "456"
            ),
            Transaction(
                "Completed",
                "11:24PM",
                "Transaction",
                "₦7,000.00",
                date = "2022-08-07",
                authType = "Debit",
                transactionSeqNumber = "TRS501499291",
                vendorStationName = "Purchase on Cupid",
                product = "DPK",
                nfcTagCode = "456"
            ),
            Transaction(
                "Pending",
                "08:24PM",
                "Transaction",
                "₦167,000.00",
                date = "2022-08-21",
                authType = "Credit",
                transactionSeqNumber = "TRS501499291",
                vendorStationName = "Wallet Top-Up",
                product = "PMS",
                nfcTagCode = "456"
            ),
            Transaction(
                "Failed",
                "11:24PM",
                "Transaction",
                "₦1,000.00",
                date = "2022-08-24",
                authType = "Debit",
                transactionSeqNumber = "TRS97814937841",
                vendorStationName = "Purchase on Cupid",
                product = "AGO",
                nfcTagCode = "456"
            ),
            Transaction(
                "Completed",
                "11:24PM",
                "Transaction",
                "₦10,000.00",
                date = LocalDate.now().toString(),
                authType = "Debit",
                transactionSeqNumber = "TRS501499291",
                vendorStationName = "Purchase on Cupid",
                product = "DPK",
                nfcTagCode = "123"
            ),
            Transaction(
                "Pending",
                "02:45PM",
                "Transaction",
                "₦99,000.00",
                date = "2022-07-31",
                authType = "Debit",
                transactionSeqNumber = "TRS5011235591",
                vendorStationName = "Purchase on Cupid",
                product = "PMS",
                nfcTagCode = "123"
            ),
            Transaction(
                "Pending",
                "11:24PM",
                "Transaction",
                "₦144,000.00",
                date = "2022-08-01",
                authType = "Credit",
                transactionSeqNumber = "TRS504299291",
                vendorStationName = "Cash Back",
                product = "AGO",
                nfcTagCode = "123"
            ),
            Transaction(
                "Pending",
                "05:30PM",
                "Transaction",
                "₦1.00",
                date = "2022-01-01",
                authType = "Debit",
                transactionSeqNumber = "TRS3819499291",
                vendorStationName = "Transaction Fee",
                product = "DPK",
                nfcTagCode = "123"
            )

        )
    }

    fun getListsOfWallets(): List<Wallet> {
        return listOf(
            Wallet(
                "Ardova",
                1,
                "150000.00",
                "2022-12-01",
                "1000000",
            ),
            Wallet(
                "Sterling",
                2,
                "460000.00",
                "2022-10-12",
                "7800000",
            )
        )
    }


}