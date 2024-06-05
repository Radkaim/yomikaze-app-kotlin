package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yomikaze_app_kotlin.R



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
            .padding(10.dp)
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
                label = {
                       Row {
                           Image(painter = painterResource(id = R.drawable.ic_email), contentDescription = "email",
                               modifier = Modifier.padding(7.dp))
                           Text(text = "Email",
                               modifier = Modifier.padding(3.dp))
                       }},
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
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
                label = {
                    Row {
                        Image(painter = painterResource(id = R.drawable.ic_eye_passwpord), contentDescription = "Password",
                            modifier = Modifier.padding(7.dp))
                        Text(text = "Password",
                            modifier = Modifier.padding(3.dp))
                    }},
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
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
            Row {
                Text(text = "Sign Up",
                    style = TextStyle(fontStyle = FontStyle.Italic),
                    modifier = Modifier.padding(
//                        top = 3.dp,
                        start = 0.dp,
                        end = 170.dp,
                        bottom = 3.dp
                    ))
                Text(text = "Forgot Password?",
                    style = TextStyle(fontStyle = FontStyle.Italic),
                    modifier = Modifier.padding(
                        top = 3.dp,
                        bottom = 3.dp)
                )
            }
           Column (
               modifier = Modifier.padding(
                   top = 20.dp,
                   bottom = 20.dp
               )
           ){
               OutlinedButton(
                   modifier = Modifier
                       .height(60.dp)
                       .width(200.dp)
                       .padding(bottom = 10.dp),
                   shape = RoundedCornerShape(12.dp),
//               elevation = ButtonDefaults.buttonColors(
//
//               ),
                   onClick = { /*TODO*/ },

                   )
               {
                   Text(text = "Login",
                       style = TextStyle(
                           fontSize = 16.sp
                       )
                   )
               }

               Button(
                   modifier = Modifier
                       .height(50.dp)
                       .width(200.dp),
                   shape = RoundedCornerShape(12.dp),
//               elevation = ButtonDefaults.buttonColors(
//
//               ),
                   onClick = { /*TODO*/ },

                   )
               {
                   Text(text = "Login with Google",
                       color = MaterialTheme.colors.surface,
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
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

