package com.example.yomikaze_app_kotlin.Presentation.Components.CoinShop

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.R

@Composable
fun CoinShopCard(
    amount: Long,
    amountPlus: Int? = null,
    price: Int,
    currency: String,
    onClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(350.dp)
            .height(55.dp)
            .offset(x = 20.dp, y = 10.dp)
            .clickable(onClick = onClicked),
        color = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(40), // Making it oval
        shadowElevation = 5.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
//                .shimmerLoadingAnimation()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    shape = RoundedCornerShape(40)
                )
                .clickable {
                    onClicked()
                }
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                //for coin icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .height(30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_coins),
                        contentDescription = "Coins Icon",
                        tint = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "$amount Coins",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                        if (amountPlus != null) {
                            Text(
                                text = "+$amountPlus coins",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.W400,
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(top = 1.dp)
                                    .offset(x = -(7).dp)
                            )
                        }
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
//                        .background(MaterialTheme.colorScheme.background)
                        .offset(x = (-20).dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(40)
                        )

                ) {
                    Text(
                        text = "$$price $currency",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W400,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}
