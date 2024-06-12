import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CardComicHistoryHome(
    comicName: String,
    image: String,
    comicChapter: String
) {

    Surface(
        modifier = Modifier
            .height(150.dp)
            .width(81.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.tertiary
    ) {
        Box(
            modifier = Modifier
                .width(61.dp)
                .height(130.dp)
                .padding(
                   5.dp
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .width(75.dp)
                )
                Text(
                    text = comicName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = comicChapter,
                    fontSize = 11.sp
//                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}