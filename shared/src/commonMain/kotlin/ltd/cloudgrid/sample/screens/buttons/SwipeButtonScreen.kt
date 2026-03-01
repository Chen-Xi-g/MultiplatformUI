package ltd.cloudgrid.sample.screens.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ltd.cloudgrid.comments.buttons.swipe.DashedSwipeButton
import ltd.cloudgrid.comments.buttons.swipe.FilledSwipeButton
import ltd.cloudgrid.comments.buttons.swipe.OutlinedSwipeButton
import ltd.cloudgrid.comments.buttons.swipe.rememberSwipeButtonState

@Preview(showBackground = true)
@Composable
fun SwipeButtonScreen() {
    val swipeState = rememberSwipeButtonState()

    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Filled Style",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FilledSwipeButton(
                state = swipeState,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Swipe to Confirm Payment",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedSwipeButton(
                state = swipeState,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Outlined Style",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            DashedSwipeButton(
                state = swipeState,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}