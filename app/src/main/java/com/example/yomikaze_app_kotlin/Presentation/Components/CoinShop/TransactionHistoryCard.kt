package com.example.yomikaze_app_kotlin.Presentation.Components.CoinShop

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.RankingComicCard.changeDateTimeFormat
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.TagComponent

@Composable
fun TransactionHistoryCard(
    type: String,
    amount: Long,
    createdAt: String,
    ) {
    val isNegative = amount < 0

    Surface(
        modifier = Modifier
            .width(370.dp)
            .height(70.dp)
            .offset(x = 10.dp, y = 10.dp),
        color = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(15), // Making it oval
        shadowElevation = 5.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isNegative) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(15)
                )
        ) {

            //for coin icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 35.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .offset(x = -(20).dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        TagComponent(status = type)
                    }
                    Text(
                        text = if (isNegative) "-$amount Coins" else "+$amount Coins",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = if (isNegative) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondaryContainer
                    )
                }

                //date
                Text(
                    text = "Date: " + changeDateTimeFormat(createdAt),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.padding(end = 20.dp, top = 40.dp)
                )
            }


        }
    }
}
