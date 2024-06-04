package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import android.graphics.Color.alpha
import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yomikaze_app_kotlin.R
import com.google.android.gms.wallet.button.ButtonConstants


@Composable
fun LoginView(loginViewModel: LoginViewModel = hiltViewModel()) {
    val state by loginViewModel.state.collectAsState()
    LoginContent(state, loginViewModel)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(state: LoginState, viewModel: LoginViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = MaterialTheme.colors.background),
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        state.hung = "hung"

        Column(
//            Alignment = Alignment.Center,

            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(200.dp)
                    .width(100.dp),
                contentScale = ContentScale.Fit
            )

            TextField(value = email,
                onValueChange = {email = it},
                label = { Text(text = "Email")},
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(
                        color = MaterialTheme.colors.secondary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(24.dp),
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )


            TextField(value = password,
                onValueChange = {password = it},
                label = { Text(text = "Password")},
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(
                        color = MaterialTheme.colors.secondary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(24.dp),
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Button(
                content = {
                    Text("Login")

                },
//                Color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
//                    .width(50.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                onClick = { viewModel.onLogin(email, password)

                },
//        ) {
////            if (state.isLoading) {
////                CircularProgressIndicator(modifier = Modifier.size(24.dp))
////            }
//        })
            )
        }
//



        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

