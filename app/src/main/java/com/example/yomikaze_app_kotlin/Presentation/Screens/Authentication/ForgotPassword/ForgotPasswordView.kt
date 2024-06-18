package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.ForgotPassword

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomeAppBar
import com.example.yomikaze_app_kotlin.R

@Composable
fun ForgotPasswordView(
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by forgotPasswordViewModel.state.collectAsState()
    forgotPasswordViewModel.setNavController(navController)

    ForgotPasswordContent(state, forgotPasswordViewModel, navController)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordContent(
    state: ForgotPasswordState,
    forgotPasswordViewModel: ForgotPasswordViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            CustomeAppBar(
                title = "Forgot Password",
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
                .offset(y = (-50).dp)
//                .padding(bottom = 30.dp)
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center

        ) {
            var email by remember { mutableStateOf("") }
            Column(
//            Alignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 40.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
//                        .padding(5.dp)
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
//                        },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
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
                        modifier = Modifier
                            .align(Alignment.Start)
//                            .padding(start = 16.dp, top = 0.dp, bottom = 0.dp)
                    )
                }
                OutlinedButton(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .height(40.dp)
                        .width(200.dp),
//                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(12.dp),
//               elevation = ButtonDefaults.buttonColors(
//
//               ),
                    onClick = {
                        //  .clickable { forgotPasswordViewModel.navigateToRegister() }
                    },

                    )
                {
                    if (state.isLoading) {
                        Text(

                            text = "SEND",
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
                    modifier = Modifier.padding(top = 12.dp),
                    text = "or",
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )

                )
                OutlinedButton(
//                   colors = buttonColors(MaterialTheme.coloS.onPrimary),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
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
                        text = "LOGIN",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp,
//                            fontStyle = FontStyle.,
                        ),
                    )
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

