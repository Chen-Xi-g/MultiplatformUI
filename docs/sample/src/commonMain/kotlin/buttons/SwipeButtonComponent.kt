package buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ltd.cloudgrid.comments.buttons.swipe.DashedSwipeButton
import ltd.cloudgrid.comments.buttons.swipe.FilledSwipeButton
import ltd.cloudgrid.comments.buttons.swipe.OutlinedSwipeButton
import ltd.cloudgrid.comments.buttons.swipe.rememberSwipeButtonState

@Composable
fun SwipeComponent() {
    val fillSwipeState = rememberSwipeButtonState()
    val outlineSwipeState = rememberSwipeButtonState()
    val dashedSwipeState = rememberSwipeButtonState()
    FilledSwipeButton(
        state = fillSwipeState,
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedSwipeButton(
        state = outlineSwipeState,
        modifier = Modifier.fillMaxWidth()
    )
    DashedSwipeButton(
        state = dashedSwipeState,
        modifier = Modifier.fillMaxWidth()
    )
}