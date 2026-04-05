package buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ltd.cloudgrid.comments.buttons.swipe.rememberSwipeButtonState
import ltd.cloudgrid.preview.buttons.DashedSwipeButton
import ltd.cloudgrid.preview.buttons.FilledSwipeButton
import ltd.cloudgrid.preview.buttons.OutlinedSwipeButton

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