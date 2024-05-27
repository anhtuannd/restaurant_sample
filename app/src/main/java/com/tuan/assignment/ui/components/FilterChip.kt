package com.tuan.assignment.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tuan.assignment.repository.Filter
import com.tuan.assignment.ui.theme.CardBackground
import com.tuan.assignment.ui.theme.LightText
import com.tuan.assignment.ui.theme.ChipSelected
import com.tuan.assignment.ui.theme.DarkText
import com.tuan.assignment.ui.theme.title2

@Composable
fun FilterChip(filter: Filter, onClick: (String, Boolean) -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(end = 27.dp)
            .clickable { onClick(filter.id, !filter.selected) }
            .height(48.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (filter.selected) ChipSelected else LightText
        ),
        shape = RoundedCornerShape(size = 24.dp)
        ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(filter.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(width = 48.dp, height = 48.dp)
            )
            Text(
                modifier = Modifier.padding(start = 56.dp, top = 14.dp, end = 24.dp),
                text = filter.name,
                style = title2,
                color = if (filter.selected) LightText else DarkText,
            )
        }
    }
}