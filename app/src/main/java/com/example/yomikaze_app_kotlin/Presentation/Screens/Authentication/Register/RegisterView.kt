package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar
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

    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "Register",
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

        Box(

            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .offset(y = (-30).dp)
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            var email by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var dateOfBirth by remember { mutableStateOf("") }
            var dateOfBirthError by remember { mutableStateOf<String?>(null) }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }

            var passwordVisible by remember { mutableStateOf(false) }
            var checked by remember { mutableStateOf(true) }
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val context = LocalContext.current
            val datePickerDialog = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay)
                    }
                    if (selectedDate.after(Calendar.getInstance())) {
                        dateOfBirthError = "Date of birth cannot be in the future"
                    } else {
                        // Định dạng ngày sinh theo kiểu `yyyy-MM-dd`
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        dateOfBirth = sdf.format(selectedDate.time)
                        dateOfBirthError = null // Clear error on successful date selection
                    }
                }, year, month, day
            ).apply {
                datePicker.maxDate = System.currentTimeMillis()
            }
            Column(
                modifier = androidx.compose.ui.Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = androidx.compose.ui.Modifier
                        .height(200.dp)
                        .width(100.dp),
                    contentScale = ContentScale.Fit
                )

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
                            tint = Color.Unspecified
                        )
                    },
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(24.dp),
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    isError = state.emailError != null
                )
                if (state.emailError != null) {
                    Text(
                        text = state.emailError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = androidx.compose.ui.Modifier
                            .align(Alignment.Start)
                    )
                }
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
                            tint = Color.Unspecified
                        )
                    },
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(24.dp),
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    isError = state.usernameError != null
                )
                if (state.usernameError != null) {
                    Text(
                        text = state.usernameError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = androidx.compose.ui.Modifier
                            .align(Alignment.Start)
                    )
                }
                TextField(
                    value = dateOfBirth,
                    onValueChange = { dateOfBirth = it },
                    label = {
                        Text(
                            text = "Date Of Birth",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done // Đặt hành động IME thành Done
                    ),
                    leadingIcon = {
                        IconButton(onClick = { datePickerDialog.show()}) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_dateofbirth),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                        }
                    },
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(24.dp),
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),

                    isError = state.usernameError != null

                )
                if (state.dateOfBirthError != null) {
                    Text(
                        text = state.dateOfBirthError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,

                        modifier = androidx.compose.ui.Modifier
                            .align(Alignment.Start)
                    )
                }
//
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
                                tint = MaterialTheme.colorScheme.onTertiary,
                                painter = image,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(24.dp),
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    isError = state.usernameError != null
                )
                if (state.passwordError != null) {
                    Text(
                        text = state.passwordError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = androidx.compose.ui.Modifier
                            .align(Alignment.Start)
                    )
                }

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
                                tint = MaterialTheme.colorScheme.onTertiary,
                                painter = image,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(24.dp),
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    isError = state.passwordError != null
                )
                if (state.confirmPasswordError != null) {
                    Text(
                        text = state.passwordError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = androidx.compose.ui.Modifier
                            .align(Alignment.Start)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = androidx.compose.ui.Modifier
                        .padding(end = 3.dp)
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        modifier = androidx.compose.ui.Modifier
                            .padding(end = 5.dp)
                            .align(Alignment.CenterVertically)

                    )
                    Text(
                        text = "Term of Service",
                        style = TextStyle(
                            fontStyle = FontStyle.Italic,
                        ),
                        modifier = androidx.compose.ui.Modifier
                            .padding(
                                top = 3.dp,
                                end = 150.dp,
                            )
                    )
                    Text(
                        text = "Sign In?",
                        style = TextStyle(fontStyle = FontStyle.Italic),
                        modifier = androidx.compose.ui.Modifier
                            .padding(
                                top = 3.dp,
                                bottom = 3.dp
                            )
                            .clickable { registerViewModel.navigateToRegister() }

                    )
                }
                OutlinedButton(
                    modifier = androidx.compose.ui.Modifier
                        .height(40.dp)
                        .width(200.dp),
                    shape = RoundedCornerShape(12.dp),


                    onClick = {
                        registerViewModel.onRegister(
                            username = username,
                            password = password,
                            fullName = username,
                            confirmPassword = confirmPassword,
                            email = email,
                            birthday = dateOfBirth,
                        )
                    }
                        )


                {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = androidx.compose.ui.Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = "Register",
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 16.sp,
                            ),
                        )

                    }
                }
            }
        }
        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = androidx.compose.ui.Modifier.padding(top = 6.dp)
            )
        }
    }
}
