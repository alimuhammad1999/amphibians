package com.example.amphibianas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        is AmphibianUiState.Loading -> LoadingScreen(modifier.fillMaxSize())
        is AmphibianUiState.Success -> MainScreen (
            amphibiabnUiState.amphibiansList,
            modifier.fillMaxWidth().padding(contentPadding)
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

/*@Composable
fun AmphibianCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(horizontal = 5.dp, vertical = 20.dp),
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
            modifier = modifier.fillMaxWidth(),
            error = painterResource(R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.loading_img)
        )
        Text(amphibian.description)
    }
}*/

@Composable
fun AmphibianCard(
    amphibian: Amphibian,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(amphibian.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = amphibian.name,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image)
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = amphibian.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = amphibian.type,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = amphibian.description,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp
                )
            }
        }
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