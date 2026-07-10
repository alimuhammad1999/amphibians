package com.example.amphibianas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibianas.ui.AmphibianViewModel
import com.example.amphibianas.ui.screens.AmphibiansApp
import com.example.amphibianas.ui.theme.AmphibianasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmphibianasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val factory: ViewModelProvider.Factory = viewModelFactory {
                        initializer {
                            val container = (application as AmphibianApplication).container
                            val repository = container.repository
                            AmphibianViewModel(repository)
                        }
                    }
                    val amphibianViewModel: AmphibianViewModel = viewModel(factory= factory)
                    AmphibiansApp(amphibianViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AmphibianasTheme {
        Greeting("Android")
    }
}