package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.AboutUs.WebViewScreen
import com.example.yomikaze_app_kotlin.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RegisterView(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by registerViewModel.state.collectAsState()
    registerViewModel.setNavController(navController)

    RegisterContent(state, registerViewModel, navController)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterContent(
    state: RegisterState,
    registerViewModel: RegisterViewModel,
    navController: NavController
) {
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var birthdayError by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var passwordVisible1 by remember { mutableStateOf(false) }

    var isTermOfServiceChecked by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var url by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
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
                title = "Register",
                navigationIcon = {
                    IconButton(onClick = {
                        if (url == null) {
                            navController.popBackStack()
                        } else {
                            url = null // Close WebView and go back to the previous screen
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                },
            )
        },
    )
    {
        if (url == null) {
            LazyColumn(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .offset(y = (-70).dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // TODO: for logo
                item {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .offset(y = (20).dp)
                            .height(200.dp)

                            .width(100.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                // TODO: for email
                item {

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = {
                            Text(
                                text = "Email",
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
                                painter = painterResource(id = R.drawable.ic_email),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primaryContainer
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
                        // isError = state.emailError != null
                    )
                    if (state.emailError != null) {
                        Text(
                            text = state.emailError.toString() ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Start,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .offset(x = -(70).dp, y = -(15).dp)
                        )
                    }
                }

                // TODO: for username
                item {
                    TextField(
                        value = username,
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
                                tint = MaterialTheme.colorScheme.primaryContainer
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

                // TODO: for birthday
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
                            tint = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Text(
                            text = birthday,
                            color = MaterialTheme.colorScheme.surfaceTint,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 50.dp, top = 5.dp)
                        )
                    }
                    if (birthdayError != null) {
                        Text(
                            text = birthdayError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .offset(y = -(10).dp)
                                .padding(start = 15.dp, bottom = 10.dp)
                        )
                    }
                }

                //  TODO: for password
                item {
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(
                                text = "Password",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done // Đặt hành động IME thành Done
                        ),
                        leadingIcon = {
                            val image = if (passwordVisible)
                                painterResource(id = R.drawable.ic_eye)
                            else
                                painterResource(id = R.drawable.ic_visibility_eye)

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    tint = MaterialTheme.colorScheme.primaryContainer,
                                    painter = image,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                shape = MaterialTheme.shapes.large,

                                )
                            .height(56.dp)
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
                        //  isError = state.passwordError != null
                    )
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError.toString() ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            ),
                            textAlign = TextAlign.Start,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .offset(x = -(100).dp, y = -(15).dp)
                        )
                    }
                }


                // TODO: for confirm password
                item {
                    TextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = {
                            Text(
                                text = "Confirm Password",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done // Đặt hành động IME thành Done
                        ),
                        leadingIcon = {
                            val image = if (passwordVisible)
                                painterResource(id = R.drawable.ic_eye)
                            else
                                painterResource(id = R.drawable.ic_visibility_eye)

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    tint = MaterialTheme.colorScheme.primaryContainer,
                                    painter = image,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 40.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                shape = MaterialTheme.shapes.large,
                            )
                            .height(56.dp)
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
                        //  isError = state.passwordError != null
                    )
                    if (state.confirmPasswordError != null) {
                        Text(
                            text = state.confirmPasswordError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            ),
                            textAlign = TextAlign.Start,
                            fontSize = 10.sp,
                            modifier = Modifier
                                .offset(x = -(120).dp, y = -(35).dp)
                                .padding(bottom = 10.dp)
                        )
                    }

                }

                // TODO: for term of service
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .offset(y = (-20).dp, x = (-30).dp)
                    ) {
                        Box() {
                            Checkbox(
                                checked = isTermOfServiceChecked,
                                onCheckedChange = { isTermOfServiceChecked = it },
                                modifier = Modifier.offset(x = (-45).dp, y = (-12).dp),
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    checkedColor = MaterialTheme.colorScheme.primary,
                                    uncheckedColor = MaterialTheme.colorScheme.primary
                                )

                            )
                            Text(
                                text = "Term of Service",
                                style = TextStyle(
                                    fontStyle = FontStyle.Italic,
                                ),
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(
                                        top = 3.dp,
                                        end = 150.dp,
                                    )
                                    .clickable { url = "https://yomikaze.org/term-of-service" }
                            )
                        }
//
//                        Text(
//                            text = "Sign In?",
//                            style = TextStyle(fontStyle = FontStyle.Italic),
//                            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                            modifier = Modifier
//                                .padding(
//                                    bottom = 20.dp
//                                )
//                                .offset(x = (30).dp)
//                                .clickable { registerViewModel.navigateToLogin() }
//
//                        )
                    }
                }

                // TODO: for register button
                item {
                    OutlinedButton(
                        modifier = Modifier
                            //.fillMaxSize()
                            .height(50.dp)
                            .offset(y = (-20).dp)
                            .width(200.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            if (isTermOfServiceChecked) {
                                registerViewModel.onRegister(
                                    username = username,
                                    password = password,
                                    fullName = username,
                                    confirmPassword = confirmPassword,
                                    email = email,
                                    birthday = birthday,
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please accept the terms of service",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.36f)
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        )
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(
                                text = "Register",
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                ),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item { WebViewScreen(url = url!!) }
            }
        }
    }
}


