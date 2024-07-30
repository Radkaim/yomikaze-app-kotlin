package com.example.yomikaze_app_kotlin.Presentation.Screens.Authentication.Login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID


@Composable
fun LoginView(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    loginState: MutableState<Boolean>? = null
) {
    val state by loginViewModel.state.collectAsState()


    loginViewModel.setNavController(navController)



    LoginContent(state, loginViewModel, navController)
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun LoginContent(state: LoginState, loginViewModel: LoginViewModel, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()


    val context = LocalContext.current
    val onCLick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val byte = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(byte)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.gcp_id))
            //.setAutoSelectEnabled(true)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                val credential = result.credential

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken
                Log.d("LoginViewModel", googleIdToken)

                if (googleIdToken != null) {
                    loginViewModel.onGoogleLogin(googleIdToken)
                }
                //  Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show(

            } catch (e: GoogleIdTokenParsingException) {
                Log.e("TokenParsingException", "Failed to sign in: ${e.message}")
                Toast.makeText(context, "Failed to sign in!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("GeneralException", "An error occurred: ${e.message}")
                Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

//    Toast.makeText(context, "You are signed in!", Toast.LENGTH_SHORT).show()


    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Login",
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
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(bottom = 100.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(200.dp)
                        .width(100.dp),
                    contentScale = ContentScale.Fit
                )
            }
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
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.large
                        )
                        .height(56.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(20.dp),
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colorScheme.surfaceTint,
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f),
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    ),
                    //   isError = state.usernameError != null
                )
                if (state.usernameError != null) {
                    Text(
                        text = state.usernameError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .offset(y = -(10).dp)
                            .padding(start = 15.dp, bottom = 10.dp)
                    )
                }
            }
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
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f),
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    ),
                    //  isError = state.passwordError != null
                )
                if (state.passwordError != null) {
                    Text(
                        text = state.passwordError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .offset(y = -(10).dp)
                            .padding(start = 12.dp, bottom = 10.dp)
                    )
                }
            }
            item {
                Row {
                    Text(
                        text = "Sign Up",
                        style = TextStyle(
                            fontStyle = FontStyle.Italic,
                        ),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                            .padding(
                                top = 3.dp,
                                start = 0.dp,
                                end = 200.dp,
                                bottom = 3.dp
                            )
                            .clickable { loginViewModel.navigateToRegister() }
                    )
                    Text(
                        text = "Forgot Password?",
                        style = TextStyle(fontStyle = FontStyle.Italic),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                            .padding(
                                top = 3.dp,
                                bottom = 3.dp
                            )
                            .clickable { loginViewModel.navigateToForgotPassword() }
                    )

                }
            }
            item {
                Column(
                    modifier = Modifier.padding(
                        top = 30.dp,
                        bottom = 70.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .height(40.dp)
                            .width(200.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.36f)
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        onClick = { loginViewModel.onLogin(username, password) },

                        )
                    {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(
                                text = "Login",
                                color = Color.White,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                ),
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
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                            .padding(
                                top = 10.dp
                            ),
                        shape = RoundedCornerShape(12.dp),
                        onClick = { onCLick() },

                        )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(20.dp)
                                .offset(x = -(10).dp, y = 0.dp),
                        )

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
            }
        }
        state.error?.let {
            Snackbar {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}


