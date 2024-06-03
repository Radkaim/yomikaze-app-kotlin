package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun LoginView(loginViewModel: LoginViewModel = hiltViewModel()) {
    val state by loginViewModel.state.collectAsState()
    LoginContent(state, loginViewModel)
}
@Composable
fun LoginContent(state: LoginState, viewModel: LoginViewModel) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        state.hung = "hung"

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(

            content = {
                Text("Login")
            },


            onClick = { viewModel.onLogin(email, password)

            },
            modifier = Modifier.fillMaxWidth()
//        ) {
////            if (state.isLoading) {
////                CircularProgressIndicator(modifier = Modifier.size(24.dp))
////            }
//        })
        )

        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

