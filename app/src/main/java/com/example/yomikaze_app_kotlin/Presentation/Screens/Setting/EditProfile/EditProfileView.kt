package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.EditProfile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.yomikaze_app_kotlin.Core.AppPreference
import com.example.yomikaze_app_kotlin.Core.Module.APIConfig
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.changeDateTimeFormat2
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun EditProfileView(
    editProfileViewModel: EditProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by editProfileViewModel.state.collectAsState()
    editProfileViewModel.setNavController(navController)
    EditProfileContent(state, editProfileViewModel, navController)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContent(
    state: EditProfileState,
    editProfileViewModel: EditProfileViewModel,
    navController: NavController

) {

    val context = LocalContext.current
    val appPreference = AppPreference(context)
    var username by remember { mutableStateOf(state.profileResponse?.name ?: "") }
    var birthday by remember { mutableStateOf(state.profileResponse?.birthday ?: "") }
    var birthdayError by remember { mutableStateOf<String?>(null) }
    var bio by remember { mutableStateOf(state.profileResponse?.bio ?: "") }
    var aboutMeError by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current


    var passwordVisible by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }


    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var url by remember { mutableStateOf<String?>(null) }
    birthday = changeDateTimeFormat2(state.profileResponse?.birthday ?: "")


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val selectedDate = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay)
            }
            if (selectedDate.after(Calendar.getInstance())) {
                birthdayError = "Date of birth cannot be in the future"
            } else {
                // Định dạng ngày sinh theo kiểu `dd-MM-yyyy`
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                birthday = sdf.format(selectedDate.time)
                birthdayError = null // Clear error on successful date selection
            }
        }, year, month, day
    ).apply {
        // Subtract one day from the current date to set maxDate to yesterday
        val yesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }.timeInMillis
        datePicker.maxDate = yesterday
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Edit Profile",
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()

                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                },
            )
        })
    {

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .wrapContentHeight()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(140.dp)
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(100.dp)
                        )
                ) {
                    AsyncImage(
                        model = imageUri
                            ?: (APIConfig.imageAPIURL.toString() + state.profileResponse?.avatar),
                        placeholder = painterResource(R.drawable.ic_profile),
                        error = painterResource(R.drawable.ic_profile),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .align(Alignment.Center)
                    )
                }
            }

            item {
                UploadImageButton(editProfileViewModel, LocalContext.current) { uri ->
                    imageUri = uri
                }
            }

            item {
                TextField(
                    value = state.profileResponse?.name ?: "",
                    onValueChange = { username = it },
                    label = {
                        Text(
                            text = "User Name",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done // Đặt hành động IME thành Done
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_personal),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.large
                        )
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(20.dp),
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.surfaceTint,
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                            alpha = 0.1f
                        ),
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    ),
                    //   isError = state.usernameError != null
                )
                if (state.usernameError != null) {
                    Text(
                        text = state.usernameError.toString() ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .offset(x = -(60).dp, y = -(15).dp)

                    )
                }
            }


            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .height(58.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.large
                        )
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable { datePickerDialog.show() },
                    contentAlignment = Alignment.CenterStart,
                ) {
                    val offsetYValue = if (birthday.isEmpty()) 0.dp else (-15).dp
                    Text(
                        text = "Date Of Birth",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .offset(y = offsetYValue)
                            .padding(start = 50.dp)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_dateofbirth),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    Text(
                        text = if (birthday.isEmpty()) birthday else birthday,
                        color = MaterialTheme.colorScheme.surfaceTint,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 50.dp, top = 5.dp)
                    )
                }

            }

            item {
                TextField(
                    value = state.profileResponse?.bio ?: "",
                    onValueChange = { bio = it },
                    label = {
                        Text(
                            text = "About Me",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                    },
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done // Đặt hành động IME thành Done
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_personal),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.large
                        )
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(20.dp),
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.surfaceTint,
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                            alpha = 0.1f
                        ),
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    ),
                    //   isError = state.usernameError != null
                )
            }


            item {
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(40.dp)
                        .width(200.dp),
//                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(12.dp),
//               elevation = ButtonDefaults.buttonColors(
//
//               ),
                    onClick = {
                        //  navController.navigate("login_route")

                    },

                    )
                {
                    if (state.isLoading) {
                        Text(

                            text = "SAVE",
                            color = MaterialTheme.colorScheme.tertiary,
                            style = TextStyle(
                                fontSize = 16.sp,
                            ),
                        )

                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp)
                        )

                    }
                }
            }
        }
    }
}

//@Composable
//fun UploadImageButton(
//    editProfileViewModel: EditProfileViewModel,
//    context: Context,
//    navController: NavController
//) {
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//
//    // Launcher để chọn ảnh từ thư viện
//    val galleryLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            imageUri = it
//            Log.d("myImageUri", "$imageUri")
//
//            // Chuyển URI thành MultipartBody.Part và tải ảnh lên
//            val multipartBodyPart = createMultipartBodyFromUri(context, uri, "image")
//            multipartBodyPart?.let { part ->
//                editProfileViewModel.uploadImage(part)
//            }
//        }
//    }
//
//    OutlinedButton(
//        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
//        modifier = Modifier
//            .padding(top = 20.dp)
//            .height(40.dp)
//            .width(200.dp),
//        shape = RoundedCornerShape(12.dp),
//        onClick = {
//            galleryLauncher.launch("image/*") // Khởi chạy ActivityResultLauncher để chọn ảnh
//        }
//    ) {
//        Text(text = "Upload Image")
//    }
//}

@Composable
fun UploadImageButton(
    editProfileViewModel: EditProfileViewModel,
    context: Context,
    onImageUriSelected: (Uri?) -> Unit
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher để chọn ảnh từ thư viện
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            onImageUriSelected(it) // Trả về URI đã chọn
//            Log.d("myImageUri", "$imageUri")

            // Chuyển URI thành MultipartBody.Part và tải ảnh lên
//            val multipartBodyPart = createMultipartBodyFromUri(context, it, "File")
//            multipartBodyPart?.let { part ->
//                editProfileViewModel.uploadImage(part)
//            }
        }
    }

    OutlinedButton(
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .padding(top = 20.dp)
            .height(40.dp)
            .width(200.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            galleryLauncher.launch("image/*") // Khởi chạy ActivityResultLauncher để chọn ảnh
        }
    ) {
        Text(text = "Upload Image")
    }
}

// Hàm để tạo MultipartBody.Part từ Uri
fun createMultipartBodyFromUri(
    context: Context,
    uri: Uri,
    multipartName: String
): MultipartBody.Part? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        // Chọn định dạng ảnh phù hợp với yêu cầu của API
        val fileFormat = "image/png" // Thay đổi thành "image/jpeg" nếu cần
        val file = File(context.cacheDir, "temp_image.png") // Đặt tên tệp tương ứng với định dạng

        FileOutputStream(file).use { outputStream ->
            bitmap.compress(
                Bitmap.CompressFormat.PNG,
                100,
                outputStream
            ) // Chuyển đổi thành PNG hoặc JPEG
        }

        val requestBody = file.asRequestBody(fileFormat.toMediaTypeOrNull())
        MultipartBody.Part.createFormData(multipartName, file.name, requestBody)
    } catch (e: Exception) {
        Log.e("UploadImageButton", "Error creating multipart body from URI: $e")
        null
    }
}
