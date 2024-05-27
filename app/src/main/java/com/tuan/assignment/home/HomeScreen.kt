package com.tuan.assignment.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuan.assignment.R
import com.tuan.assignment.ui.components.FilterChip
import com.tuan.assignment.ui.components.RestaurantCard

@Composable
fun HomeScreen(
    onRestaurantClick: (String) -> Unit,
    modifier: Modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_umain),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier
                .size(width = 54.dp, height = 54.dp)
        )
        
        Spacer(modifier = Modifier.height(22.dp))

        val filters by viewModel.filterStream.collectAsStateWithLifecycle()
        LazyRow {
            items(items = filters, key = { it.id }) {
                FilterChip(it) { id, selected -> viewModel.setFilterSelected(id, selected) }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val restaurants by viewModel.restaurantStream.collectAsStateWithLifecycle()
        LazyColumn {
            items(items = restaurants, key = { it.id }) {
                RestaurantCard(restaurant = it, onClick = onRestaurantClick)
            }
        }
    }
}