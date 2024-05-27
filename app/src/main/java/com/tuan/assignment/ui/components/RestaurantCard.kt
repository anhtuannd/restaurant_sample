package com.tuan.assignment.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tuan.assignment.R
import com.tuan.assignment.repository.Restaurant
import com.tuan.assignment.ui.theme.CardBackground
import com.tuan.assignment.ui.theme.DarkText
import com.tuan.assignment.ui.theme.Rating
import com.tuan.assignment.ui.theme.Subtitle
import com.tuan.assignment.ui.theme.footer1
import com.tuan.assignment.ui.theme.rating
import com.tuan.assignment.ui.theme.subtitle1
import com.tuan.assignment.ui.theme.title1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantCard(
    restaurant: Restaurant,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        onClick = { onClick(restaurant.id) }
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(restaurant.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(132.dp),
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = restaurant.name,
                    style = title1,
                    color = DarkText
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = restaurant.filters.joinToString(separator = " - ") { it.name },
                    style = subtitle1,
                    color = Subtitle
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = null,
                        modifier = Modifier.size(width = 10.dp, height = 10.dp)
                    )
                    Text(
                        text = String.format("%d mins", restaurant.deliveryTimeInMinutes),
                        style = footer1,
                        color = Rating,
                        modifier = Modifier.padding(start = 3.dp)
                    )
                }
            }
            Row(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    modifier = Modifier.size(width = 12.dp, height = 12.dp)
                )
                Text(
                    text = String.format("%.1f", restaurant.rating),
                    style = rating,
                    color = Rating,
                    modifier = Modifier.padding(start = 3.dp)
                )
            }
        }
    }
}
