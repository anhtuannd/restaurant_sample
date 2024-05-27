package com.tuan.assignment.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tuan.assignment.R
import com.tuan.assignment.repository.Restaurant
import com.tuan.assignment.ui.components.DetailCard

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onNavigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.detailUiState.collectAsStateWithLifecycle()
    if (uiState is DetailUiState.Success) {
        val restaurant = (uiState as DetailUiState.Success).data
        Box(modifier = modifier) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(restaurant.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.FillWidth
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 41.dp, start = 22.dp)
                    .clickable { onNavigateBack() }
            )
            val openState by viewModel.openUiState.collectAsStateWithLifecycle()
            DetailCard(restaurant, openState)
        }
    }

}