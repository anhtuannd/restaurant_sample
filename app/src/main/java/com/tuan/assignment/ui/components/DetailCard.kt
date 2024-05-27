package com.tuan.assignment.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tuan.assignment.detail.OpenUiState
import com.tuan.assignment.repository.Restaurant
import com.tuan.assignment.ui.theme.CardBackground
import com.tuan.assignment.ui.theme.Negative
import com.tuan.assignment.ui.theme.Positive
import com.tuan.assignment.ui.theme.Subtitle
import com.tuan.assignment.ui.theme.headline1
import com.tuan.assignment.ui.theme.headline2
import com.tuan.assignment.ui.theme.title1

@Composable
fun DetailCard(restaurant: Restaurant, openState: OpenUiState) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 175.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = restaurant.name,
                style = headline1,
                modifier = Modifier
                    .height(42.dp)
                    .wrapContentSize()
            )
            Text(
                text = restaurant.filters.joinToString(separator = " - ") { it.name },
                style = headline2,
                color = Subtitle,
                modifier = Modifier
                    .height(35.dp)
                    .wrapContentSize()
            )
            if (openState is OpenUiState.Success) {
                val isOpen = openState.data
                Text(
                    text = if (isOpen) "Open" else "Closed",
                    style = title1,
                    color = if (isOpen) Positive else Negative,
                    modifier = Modifier
                        .height(35.dp)
                        .wrapContentSize()
                )
            }
        }
    }
}
