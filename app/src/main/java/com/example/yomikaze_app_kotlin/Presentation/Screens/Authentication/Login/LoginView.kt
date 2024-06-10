package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults.buttonColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar
import com.example.yomikaze_app_kotlin.R



@Composable
fun LoginView(loginViewModel: LoginViewModel = hiltViewModel(),
              navController: NavController
) {
    val state by loginViewModel.state.collectAsState()

    LoginContent(state, loginViewModel, navController)
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(state: LoginState, viewModel: LoginViewModel, navController: NavController) {

    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "Notification",
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
            modifier = Modifier
                .fillMaxSize()
//            .padding(10.dp)
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
//        state.hung = "hung"

            Column(
//            Alignment = Alignment.Center,

                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(200.dp)
                        .width(100.dp),
                    contentScale = ContentScale.Fit
                )

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
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    },
//                        },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
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
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 16.dp, top = 0.dp, bottom = 0.dp)
                    )
                }

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
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
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
                if (state.passwordError != null) {
                    Text(
                        text = state.passwordError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 16.dp, top = 1.dp, bottom = 2.dp)
                    )
                }
                Row {
                    Text(
                        text = "Sign Up",
//                    color = MaterialTheme.colors.surface,
                        style = TextStyle(
                            fontStyle = FontStyle.Italic,
//                        color = MaterialTheme.colors.surface
                        ),
                        modifier = Modifier.padding(
                            top = 3.dp,
                            start = 0.dp,
                            end = 200.dp,
                            bottom = 3.dp
                        ) .clickable {  }
                    )
                    Text(
                        text = "Forgot Password?",
                        style = TextStyle(fontStyle = FontStyle.Italic),
                        modifier = Modifier.padding(
                            top = 3.dp,
                            bottom = 3.dp
                        ),
                    )

                }
                Column(
                    modifier = Modifier.padding(
                        top = 20.dp,
                        bottom = 20.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .height(40.dp)
                            .width(200.dp),
//                        .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(12.dp),
//               elevation = ButtonDefaults.buttonColors(
//
//               ),
                        onClick = {
                            viewModel.onLogin(username, password)
                        },

                        )
                    {
                        if (state.isLoading) {
                            Text(
                                text = "Login",
                                color = Color.Black,
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

                    Text(
                        text = "or",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    )
                    OutlinedButton(
//                   colors = buttonColors(MaterialTheme.coloS.onPrimary),
                        colors = buttonColors(MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                            .padding(
                                top = 10.dp
                            ),
//                       .background(color = MaterialTheme.colors.secondary),
                        shape = RoundedCornerShape(12.dp),
//               elevation = ButtonDefaults.buttonColors(
//
//               ),
                        onClick = { /*TODO*/ },

                        )
                    {
                        Text(
                            text = "Login with Google",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Italic,
                            ),
                        )
                    }
                }

//            Button(
//                content = {
//                    Text("Login with Google")
//
//                },
////                Color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
//                modifier = Modifier
//                    .fillMaxWidth()
////                    .width(50.dp)
//                    .height(50.dp),
//                shape = RoundedCornerShape(24.dp),
//                onClick = { viewModel.onLogin(email, password)
//
//                },
////        ) {
//////            if (state.isLoading) {
//////                CircularProgressIndicator(modifier = Modifier.size(24.dp))
//////            }
////        })
//            )

            }
//


            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

