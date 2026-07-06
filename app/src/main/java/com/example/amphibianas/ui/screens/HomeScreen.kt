package com.example.amphibianas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibianas.R
import com.example.amphibianas.model.Amphibian
import com.example.amphibianas.ui.AmphibianUiState

@Composable
fun HomeScreen(
    amphibiabnUiState: AmphibianUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    retryAction: () -> Unit
) {
    when(amphibiabnUiState) {
        is AmphibianUiState.Loading -> LoadingScreen(modifier.fillMaxWidth())
        is AmphibianUiState.Success -> MainScreen (
            amphibiabnUiState.amphibiansList, modifier.fillMaxWidth()
        )
        is AmphibianUiState.Error -> ErrorScreen(amphibiabnUiState.message, retryAction)
    }
}

@Composable
fun ErrorScreen(error: String, retry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = "Connection error"
        )
        Text("Failed to Load List, $error")
        Button(onClick = retry) {
            Text("Retry")
        }
    }
}

@Composable
fun MainScreen(amphibiansList: List<Amphibian>, modifier: Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {

        items(amphibiansList.size) {
            AmphibianCard(amphibian = amphibiansList[it])
        }

    }
}

@Composable
fun AmphibianCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Text(
            "${amphibian.name} (${amphibian.type})",
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(amphibian.imgSrc)
                .crossfade(true)
                .build(),
            contentDescription = "Amphibian Image",
            modifier = modifier.fillMaxWidth()
//            contentScale = ContentScale.Crop,
//            error = painterResource(R.drawable.ic_broken_image),
//            placeholder = painterResource(R.drawable.loading_img),
//            contentDescription = stringResource(R.string.mars_photo)
        )
        Text(amphibian.description)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Loading Icon",
        modifier = modifier.size(200.dp)
    )
}