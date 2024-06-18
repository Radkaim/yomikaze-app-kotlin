import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.yomikaze_app_kotlin.Presentation.Components.ComicCard.ShareComponents.IconAndNumbers
import com.example.yomikaze_app_kotlin.R

@Composable
fun CardComicHistoryHome(
    comicName: String,
    image: String,
    comicChapter: String
) {

    Surface(
        modifier = Modifier
            .height(190.dp) // Adjusted height for better visual balance
            .width(90.dp),
        shape = RoundedCornerShape(8.dp),
//        color = MaterialTheme.
    ) {

        Column(
            modifier = Modifier
                .height(180.dp) // Adjusted height for better visual balance
                .width(90.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Card(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .height(200.dp) // Adjusted height for better visual balance
                    .width(90.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary // Set the background color of the card to white
                )

//                    .wrapContentHeight(),

            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(
                        start = 5.dp,
                        top = 4.dp,
                        bottom = 5.dp
                    ) // Padding inside the card


                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier
                            .height(110.dp) // Adjusted height for better visual balance
                            .width(82.dp)
                            .padding(end = 2.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.Crop,
//                    .padding(1.dp),
                    )
                    // Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        modifier = Modifier.offset(x = (3).dp, y = (5).dp)
                    ) {
                        Text(
                            text = comicName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 2.dp, vertical = 1.dp)
                        )
                        Text(
                            text = comicChapter,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 2.dp, vertical = 1.dp)
                        )
                    }
                    Column (
                        modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
                    ){
                        IconAndNumbers(
                            icon = R.drawable.ic_star_fill,
                            iconColor = MaterialTheme.colorScheme.surface,
                            numberColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            numberWeight = FontWeight.Medium,
                            iconWidth = 13,
                            iconHeight = 14,
                            numberSize = 12,

                            )
                    }
                }

            }
        }
    }
}
//}