@file:OptIn(ExperimentalFoundationApi::class)

package com.example.yomikaze_app_kotlin.Presentation.Screens.Setting.CoinShop

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yomikaze_app_kotlin.Presentation.Components.CoinShop.CoinShopCard
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.CheckNetwork
import com.example.yomikaze_app_kotlin.Presentation.Components.Network.NoNetworkAvailable
import com.example.yomikaze_app_kotlin.Presentation.Components.ShimmerLoadingEffect.CoinShopCardShimmerLoading
import com.example.yomikaze_app_kotlin.Presentation.Components.TopBar.CustomAppBar
import com.example.yomikaze_app_kotlin.R
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheet.CustomerConfiguration
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet


@Composable
fun CoinShopView(
    navController: NavController,
    coinShopViewModel: CoinShopViewModel = hiltViewModel()
) {

    val state by coinShopViewModel.state.collectAsState()

    if (CheckNetwork()) {
        CoinShopContent(
            navController = navController,
            coinShopViewModel = coinShopViewModel,
            coinShopState = state
        )
    } else {
        NoNetworkAvailable()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CoinShopContent(
    navController: NavController,
    coinShopViewModel: CoinShopViewModel,
    coinShopState: CoinShopState
) {

    LaunchedEffect(Unit) {
        coinShopViewModel.getCoinPricing(1, 30)
    }

    LaunchedEffect(Unit) {
        coinShopViewModel.getProfile()
    }
    LaunchedEffect(key1 = coinShopViewModel.isPaymentSuccess.value) {
        if (coinShopViewModel.isPaymentSuccess.value) {
            coinShopViewModel.getProfile()
            coinShopViewModel.isPaymentSuccess.value = false
        }
    }

    val paymentSheet = rememberPaymentSheet { paymentSheetResult ->
        onPaymentSheetResult(paymentSheetResult, coinShopViewModel)
    }

    var customerConfig by remember { mutableStateOf<CustomerConfiguration?>(null) }
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    LaunchedEffect(key1 = coinShopViewModel.coinPricingId.value) {
        coinShopViewModel.coinPricingId.value?.let {
            coinShopViewModel.getPaymentSheetResponse(it)
        }
    }

    if (coinShopState.isGetPaymentSheetResponseSuccess) {
        LaunchedEffect(context) {

            customerConfig = PaymentSheet.CustomerConfiguration(
                coinShopState.paymentSheetResponse!!.customer,
                coinShopState.paymentSheetResponse!!.ephemeralKey
            )
            paymentIntentClientSecret =
                coinShopState.paymentSheetResponse!!.paymentIntentClientSecret
            Log.d("CoinShopViewModel", "CurrentConfig1: ${customerConfig.toString()}")
            PaymentConfiguration.init(context, coinShopState.paymentSheetResponse!!.publishableKey)


            val currentConfig = customerConfig
            val currentClientSecret = paymentIntentClientSecret

            if (currentConfig != null && currentClientSecret != null) {
                Log.d("CoinShopViewModel", "CurrentConfig2: $currentConfig")
                coinShopViewModel.isPaymentSheetVisible.value = true
                presentPaymentSheet(paymentSheet, currentConfig, currentClientSecret)

            }
        }
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Shopping Coins",
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
        },
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 10.dp)
                .padding(bottom = 60.dp), // for show all content

        ) {
            item {
                CoinAvailableContent(
                    coinShopState.profileResponse?.balance ?: 0,
                    onReloadClicked = {
                        if (coinShopViewModel.checkUserIsLogin()) {
                            coinShopViewModel.getProfile()
                        } else {
                            navController.navigate("login")
                        }
                    })
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (coinShopViewModel.checkUserIsLogin()) "Buy coin to unlock and download chapters" else "Please login to buy coins",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W300,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
            }
            //shimmer loading
            item {
                if (coinShopState.isGetCoinPricingLoading) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp), // 15.dp space between each card
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        repeat(6) {
                            CoinShopCardShimmerLoading()

                        }
                    }
                }
            }

            //divider
            item {
                if (!coinShopState.isGetCoinPricingLoading) {
                    Divider(
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.offset(y = (-30).dp)
                    )
                }
            }

            items(coinShopState.coinPricingList) { coinPricing ->
                Column(
                    //verticalArrangement = Arrangement.spacedBy(20.dp), // 15.dp space between each card
                    modifier = Modifier
                        .offset(y = (-50).dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    if (!coinShopState.isGetCoinPricingLoading) {
                        CoinShopCard(
                            amount = coinPricing.amount,
                            price = coinPricing.price,
                            currency = coinPricing.currency,
                            onClicked = {

                                if (coinShopViewModel.checkUserIsLogin()) {
                                    coinShopViewModel.coinPricingId.value = coinPricing.id
                                } else {
                                    Toast.makeText(
                                        context,
                                        "You need to login to buy coins",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate("login_route")
                                }


                            }
                        )
                    }
                }
            }

        }
    }
}



@Composable
fun CoinAvailableContent(
    coin: Long,
    onReloadClicked: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp),

            ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_coins),
                contentDescription = "Coins Icon",
                tint = MaterialTheme.colorScheme.scrim,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
            Text(
                text = if (coin > 1) "$coin Coins" else "$coin Coin",
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colorScheme.onSecondary
            )
            val iconReloadClick = mutableStateOf(false)

            IconButton(onClick = {
                iconReloadClick.value = true
                onReloadClicked()
                iconReloadClick.value = false
            }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_reload),
                        contentDescription = "Coins Icon",
                        tint = MaterialTheme.colorScheme.surfaceTint,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
            }
        }
    }
}


private fun presentPaymentSheet(
    paymentSheet: PaymentSheet,
    customerConfig: CustomerConfiguration,
    paymentIntentClientSecret: String
) {

    paymentSheet.presentWithPaymentIntent(
        paymentIntentClientSecret,
        PaymentSheet.Configuration(
            merchantDisplayName = "Yomikaze",
            customer = customerConfig,
            // Set `allowsDelayedPaymentMethods` to true if your business handles
            // delayed notification payment methods like US bank accounts.
            googlePay = PaymentSheet.GooglePayConfiguration(
                environment = PaymentSheet.GooglePayConfiguration.Environment.Test,
                countryCode = "US",
            ),
            allowsDelayedPaymentMethods = true
        )

    )
}

fun onPaymentSheetResult(
    paymentSheetResult: PaymentSheetResult,
    coinShopViewModel: CoinShopViewModel
) {
    // implemented in the next steps

    when (paymentSheetResult) {
        is PaymentSheetResult.Canceled -> {
            coinShopViewModel.isPaymentSheetVisible.value = false
            coinShopViewModel.coinPricingId.value = null
            coinShopViewModel.isPaymentSuccess.value = false
            print("Canceled")
        }

        is PaymentSheetResult.Failed -> {
            Log.e("CoinShopViewModel", "Failed: ${paymentSheetResult.error}")
            print("Error: ${paymentSheetResult.error}")
        }

        is PaymentSheetResult.Completed -> {
            // Display for example, an order confirmation screen
            coinShopViewModel.isPaymentSuccess.value = true
            print("Completed")
        }
    }
    coinShopViewModel.coinPricingId.value = null // Reset coinPricingId after payment result
    coinShopViewModel.isPaymentSheetVisible.value = false // Reset visibility flag
}
