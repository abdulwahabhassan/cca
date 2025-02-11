package com.smartflowtech.cupidcustomerapp.ui.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.gson.reflect.TypeToken
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber
import java.io.*
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

    fun getImageUri(context: Context, bitmap: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                bitmap,
                "SFT_${System.currentTimeMillis()}",
                null
            )
        return Uri.parse(path)
    }

    @OptIn(ExperimentalPermissionsApi::class)
    fun getTextToShowGivenPermissions(
        permissions: List<PermissionState>,
        shouldShowRationale: Boolean
    ): String {
        val revokedPermissionsSize = permissions.size
        if (revokedPermissionsSize == 0) return ""

        val textToShow = StringBuilder()
        textToShow.append("${if (revokedPermissionsSize == 1) "Permission" else "Permissions"} to ")

        for (i in permissions.indices) {
            textToShow.append(
                permissions[i].permission.substringAfter("android.permission.")
                    .replace("_", " ")
                    .lowercase(Locale.ROOT)
            )
            when {
                revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                    textToShow.append(" and ")
                }
                i == revokedPermissionsSize - 1 -> {
                    textToShow.append(" ")
                }
                else -> {
                    textToShow.append(", ")
                }
            }
        }
        textToShow.append(if (revokedPermissionsSize == 1) "is" else "are")
        textToShow.append(
            if (shouldShowRationale) {
                if (revokedPermissionsSize == 1)
                    " important. Please grant it for the app to function properly."
                else
                    " important. Please grant all of them for the app to function properly."
            } else {
                if (revokedPermissionsSize == 1)
                    " denied. The app cannot function properly without it. Please grant this permission."
                else
                    " denied. The app cannot function properly without them. Please grant these permissions."
            }
        )
        return textToShow.toString()
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
            Timber.d("UpdateProfileData $data")
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
                ussdCode = "*393*044*738#"
            ),
            Bank(
                id = "2",
                name = "Citibank",
                icon = R.drawable.ic_citi,
                ussdCode = "*393*023*738#"
            ),
            Bank(
                id = "3",
                name = "Diamond Bank",
                icon = R.drawable.ic_diamond,
                ussdCode = "*393*063*738#"
            ),
            Bank(
                id = "5",
                name = "Ecobank Nigeria",
                icon = R.drawable.ic_eco,
                ussdCode = "*393*050*738#"
            ),
            Bank(
                id = "6",
                name = "Fidelity Bank Nigeria",
                icon = R.drawable.ic_fidelity,
                ussdCode = "*393*070*738#"
            ), Bank(
                id = "7",
                name = "First Bank of Nigeria",
                icon = R.drawable.ic_first_bank,
                ussdCode = "*393*011*738#"
            ),
            Bank(
                id = "8",
                name = "First City Monument Bank",
                icon = R.drawable.ic_fcmb,
                ussdCode = "*393*214*738#"
            ),
            Bank(
                id = "9",
                name = "Guaranty Trust Bank",
                icon = R.drawable.ic_gtb,
                ussdCode = "*393*058*738#"
            ),
            Bank(
                id = "10",
                name = "Heritage Bank Plc",
                icon = R.drawable.ic_hb,
                ussdCode = "*393*030*738#"
            ),
            Bank(
                id = "11",
                name = "Jaiz Bank",
                icon = R.drawable.ic_jaiz,
                ussdCode = "*393*301*738#"
            ),
            Bank(
                id = "12",
                name = "Keystone Bank Limited",
                icon = R.drawable.ic_keystone,
                ussdCode = "*393*082*738#"
            ),
            Bank(
                id = "13",
                name = "Providus Bank Plc",
                icon = R.drawable.ic_providus,
                ussdCode = "*393*101*738#"
            ),
            Bank(
                id = "14",
                name = "Polaris Bank",
                icon = R.drawable.ic_polaris,
                ussdCode = "*393*076*738#"
            ), Bank(
                id = "15",
                name = "Stanbic IBTC Bank Nigeria Limited",
                icon = R.drawable.ic_stanbic,
                ussdCode = "*393*221*738#"
            ),
            Bank(
                id = "16",
                name = "Standard Chartered Bank",
                icon = R.drawable.ic_standard_chartered,
                ussdCode = "*393*068*738#"
            ),
            Bank(
                id = "17",
                name = "Sterling Bank",
                icon = R.drawable.ic_sterling,
                ussdCode = "*393*232*738#"
            ), Bank(
                id = "18",
                name = "Suntrust Bank Nigeria Limited",
                icon = R.drawable.ic_suntrust,
                ussdCode = "*393*100*738#"
            ),
            Bank(
                id = "19",
                name = "Union Bank of Nigeria",
                icon = R.drawable.ic_union,
                ussdCode = "*393*032*738#"
            ),
            Bank(
                id = "20",
                name = "United Bank for Africa",
                icon = R.drawable.ic_uba,
                ussdCode = "*393*033*738#"
            ),
            Bank(
                id = "21",
                name = "Unity Bank Plc",
                icon = R.drawable.ic_unity,
                ussdCode = "*393*215*738#"
            ),
            Bank(
                id = "22",
                name = "Wema Bank",
                icon = R.drawable.ic_wema,
                ussdCode = "*393*035*738#"
            ),
            Bank(
                id = "23",
                name = "Zenith Bank",
                icon = R.drawable.ic_zenith,
                ussdCode = "*393*057*738#"
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
//            SettingsItem(
//                "Appearance",
//                "Switch between light and dark theme",
//                R.drawable.ic_appearance
//            ),
            SettingsItem(
                "Logout",
                "Log out of app",
                R.drawable.ic_logout
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

    val defaultPeriods: List<String>
        get() = listOf(
            Period.TODAY.name,
            Period.ONE_WEEK.name,
            Period.ONE_MONTH.name
        )

    fun getListOfStationsFilter(): List<String> {
        return listOf(
            StationFilter.STATE.name,
            StationFilter.CITY.name
        )
    }

    fun getListOfTransactions(): List<Transaction> {
        return listOf(
            Transaction(
                id = 1,
                "Completed",
                "12:24AM",
                "Transaction",
                "₦67,000.00",
                date = "2022-08-01",
                authType = "Credit",
                transactionSeqNumber = "TRS90399291",
                vendorStationName = "Purchase on CUPID_PROD",
                product = "PMS",
                nfcTagCode = "123",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 2,
                "Pending",
                "11:24PM",
                "Transaction",
                "₦30,000.00",
                date = "2022-08-15",
                authType = "Credit",
                transactionSeqNumber = "TRS765599291",
                vendorStationName = "Wallet Top-Up",
                product = "PMS",
                nfcTagCode = "456",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 3,
                "Completed",
                "11:20AM",
                "Transaction",
                "₦12,000.00",
                date = "2022-08-30",
                authType = "Credit",
                transactionSeqNumber = "TRS765599291",
                vendorStationName = "Wallet Top-Up",
                product = "AGO",
                nfcTagCode = "123",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 4,
                "Failed",
                "01:24PM",
                "Transaction",
                "₦17,000.00",
                date = "2022-07-01",
                authType = "Debit",
                transactionSeqNumber = "TRS723599291",
                vendorStationName = "Mobile Transfer",
                product = "AGO",
                nfcTagCode = "123",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 5,
                "Completed",
                "11:24PM",
                "Transaction",
                "₦65,000.00",
                date = "2022-07-15",
                authType = "Debit",
                transactionSeqNumber = "TRS0949482392",
                vendorStationName = "Mobile Transfer",
                product = "DPK",
                nfcTagCode = "456",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 6,
                "Completed",
                "11:24PM",
                "Transaction",
                "₦7,000.00",
                date = "2022-08-07",
                authType = "Debit",
                transactionSeqNumber = "TRS501499291",
                vendorStationName = "Purchase on CUPID_PROD",
                product = "DPK",
                nfcTagCode = "456",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 7,
                "Pending",
                "08:24PM",
                "Transaction",
                "₦167,000.00",
                date = "2022-08-21",
                authType = "Credit",
                transactionSeqNumber = "TRS501499291",
                vendorStationName = "Wallet Top-Up",
                product = "PMS",
                nfcTagCode = "456",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 8,
                "Failed",
                "11:24PM",
                "Transaction",
                "₦1,000.00",
                date = "2022-08-24",
                authType = "Debit",
                transactionSeqNumber = "TRS97814937841",
                vendorStationName = "Purchase on CUPID_PROD",
                product = "AGO",
                nfcTagCode = "456",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 9,
                "Completed",
                "11:24PM",
                "Transaction",
                "₦10,000.00",
                date = LocalDate.now().toString(),
                authType = "Debit",
                transactionSeqNumber = "TRS501499291",
                vendorStationName = "Purchase on CUPID_PROD",
                product = "DPK",
                nfcTagCode = "123",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 10,
                "Pending",
                "02:45PM",
                "Transaction",
                "₦99,000.00",
                date = "2022-07-31",
                authType = "Debit",
                transactionSeqNumber = "TRS5011235591",
                vendorStationName = "Purchase on CUPID_PROD",
                product = "PMS",
                nfcTagCode = "123",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 11,
                "Pending",
                "11:24PM",
                "Transaction",
                "₦144,000.00",
                date = "2022-08-01",
                authType = "Credit",
                transactionSeqNumber = "TRS504299291",
                vendorStationName = "Cash Back",
                product = "AGO",
                nfcTagCode = "123",
                dateTime = "2022-01-01 12:00:00"
            ),
            Transaction(
                id = 12,
                "Pending",
                "05:30PM",
                "Transaction",
                "₦1.00",
                date = "2022-01-01",
                authType = "Debit",
                transactionSeqNumber = "TRS3819499291",
                vendorStationName = "Transaction Fee",
                product = "DPK",
                nfcTagCode = "123",
                dateTime = "2022-01-01 12:00:00"
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
                1,
                "VLX_393"
            ),
            Wallet(
                "Sterling",
                2,
                "460000.00",
                "2022-10-12",
                "7800000",
                2,
                "VLX_390"
            )
        )
    }

    fun copyTextToClipBoard(ctx: Context, text: String, label: String = "") {
        (ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
            .setPrimaryClip(
                ClipData.newPlainText(label, text)
            )
    }


}