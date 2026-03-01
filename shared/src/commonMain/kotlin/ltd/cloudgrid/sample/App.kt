package ltd.cloudgrid.sample

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ltd.cloudgrid.sample.nav.NavBackStack

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        NavBackStack()
    }
}
