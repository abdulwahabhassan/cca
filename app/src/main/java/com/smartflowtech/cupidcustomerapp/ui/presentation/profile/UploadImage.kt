package com.smartflowtech.cupidcustomerapp.ui.presentation.profile

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.smartflowtech.cupidcustomerapp.contract.OpenMediaStore
import com.smartflowtech.cupidcustomerapp.model.domain.UploadImageOption
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import com.smartflowtech.cupidcustomerapp.ui.utils.Util.getImageUri
import com.smartflowtech.cupidcustomerapp.ui.utils.Util.getTextToShowGivenPermissions
import timber.log.Timber


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UploadImage(
    onImageSelected: (String) -> Unit
) {

    val ctx = LocalContext.current
    val options = Util.getListOfUploadImageOptions()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                try {
                    val uri = getImageUri(ctx, bitmap)
                    onImageSelected(uri.toString())
                } catch (e: Exception) {
                    Toast.makeText(ctx, "Can't upload image!", Toast.LENGTH_LONG).show()
                }
            }
        }
    )

    val galleryLauncher = rememberLauncherForActivityResult(OpenMediaStore()) { uri ->
        if (uri.isNotEmpty()) {
            Toast.makeText(ctx, uri, Toast.LENGTH_LONG).show()
            Timber.d(uri)
            onImageSelected(uri)
        } else Toast.makeText(ctx, "No Image was selected", Toast.LENGTH_LONG).show()

    }

    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    )

    if (multiplePermissionsState.allPermissionsGranted) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            userScrollEnabled = true,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "Upload Image",
                        color = Color.Black,
                        fontFamily = AthleticsFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(options) { item ->
                UploadImageOption(
                    item
                ) { uploadOption: UploadImageOption ->
                    if (uploadOption.title == "Choose from gallery") {
                        galleryLauncher.launch(Unit)
                    } else if (uploadOption.title == "Take a photo") {
                        cameraLauncher.launch(null)
                    }
                }

            }
        }
    } else {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                getTextToShowGivenPermissions(
                    multiplePermissionsState.revokedPermissions,
                    multiplePermissionsState.shouldShowRationale
                ),
                color = darkBlue,
                fontSize = 12.sp,
                modifier = Modifier
                    .background(
                        color = transparentBlue,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                Text("Grant permission")
            }
        }
    }


}


@Composable
fun UploadImageOption(
    data: UploadImageOption,
    onClick: (transaction: UploadImageOption) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick(data)
                }
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(
                    id = data.icon
                ),
                contentDescription = "Icon",
                tint = Color.Unspecified
            )

            Column(
                Modifier
                    .weight(2f, true)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = data.title,
                    fontFamily = AthleticsFontFamily,
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = data.description,
                        fontSize = 14.sp,
                        color = grey
                    )
                }
            }
        }
        Divider(
            color = lineGrey,
            thickness = 0.5.dp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UploadImageOptionPreview() {
    CupidCustomerAppTheme {
//        UploadImage({})
    }

}
