package ltd.cloudgrid.comments.buttons.swipe

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ltd.cloudgrid.components.buttons.generated.resources.Res
import ltd.cloudgrid.components.buttons.generated.resources.ic_arrow_right_alt
import ltd.cloudgrid.components.buttons.generated.resources.ic_check_circle
import org.jetbrains.compose.resources.painterResource


/**
 * A pre-styled [SwipeButton] with a solid filled background.
 *
 * @param modifier The [Modifier] to be applied to the button.
 * @param text The label displayed inside the swipe area.
 * @param state The state of the button.
 * @param containerColor The background color of the swipe track.
 */
@Composable
fun FilledSwipeButton(
    modifier: Modifier = Modifier,
    text: String = "Swipe to confirm",
    state: SwipeButtonState = rememberSwipeButtonState(),
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    var isCompleted by remember { mutableStateOf(false) }
    SwipeButton(
        modifier = modifier.height(50.dp),
        state = state,
        cornerRadius = 25.dp,
        background = {
            Spacer(Modifier.fillMaxSize().background(containerColor.copy(0.5f)))
        },
        content = { progress ->
            Text(text, color = Color.White.copy(alpha = (1f - progress).coerceAtLeast(0f)))
        },
        indicator = { progress ->
            SwipeIndicator(
                progress = progress,
                modifier = Modifier.background(containerColor)
            )
        },
        completedContent = {
            LoadingIndicator(isCompleted, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    )

    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            delay(2000L)
            isCompleted = true
            delay(2000L)
            isCompleted = false
            state.reset()
        }
    }
}

/**
 * A pre-styled [SwipeButton] with an outlined border and transparent background.
 *
 * @param modifier The [Modifier] to be applied to the button.
 * @param text The label displayed inside the swipe area.
 * @param state The state of the button.
 * @param outlineColor The color of the border and the draggable handle.
 */
@Composable
fun OutlinedSwipeButton(
    modifier: Modifier = Modifier,
    text: String = "Swipe to confirm",
    state: SwipeButtonState = rememberSwipeButtonState(),
    outlineColor: Color = MaterialTheme.colorScheme.primary,
) {
    var isCompleted by remember { mutableStateOf(false) }

    SwipeButton(
        modifier = modifier.height(50.dp),
        state = state,
        cornerRadius = 25.dp,
        background = {
            Spacer(
                Modifier
                    .fillMaxSize()
                    .border(1.dp, outlineColor, CircleShape)
            )
        },
        content = { progress ->
            Text(
                text
            )
        },
        indicator = { progress ->
            SwipeIndicator(
                progress = progress,
                tint = outlineColor,
                modifier = Modifier.border(1.dp, outlineColor, CircleShape)
            )
        },
        completedContent = {
            LoadingIndicator(isCompleted, color = outlineColor)
        }
    )

    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            delay(2000L)
            isCompleted = true
            delay(2000L)
            isCompleted = false
            state.reset()
        }
    }
}

/**
 * A pre-styled [SwipeButton] featuring a dashed border effect.
 *
 * @param modifier The [Modifier] to be applied to the button.
 * @param text The label displayed inside the swipe area.
 * @param state The state of the button.
 * @param indicatorColor The color of the draggable handle.
 */
@Composable
fun DashedSwipeButton(
    modifier: Modifier = Modifier,
    text: String = "Swipe to confirm",
    state: SwipeButtonState = rememberSwipeButtonState(),
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
) {
    var isCompleted by remember { mutableStateOf(false) }

    SwipeButton(
        modifier = modifier.height(50.dp),
        state = state,
        cornerRadius = 25.dp,
        background = {
            Spacer(
                Modifier
                    .fillMaxSize()
                    .drawBehind {
                        drawRoundRect(
                            color = indicatorColor,
                            style = Stroke(
                                width = 2f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
                            ),
                            cornerRadius = CornerRadius(size.height / 2)
                        )
                    })
        },
        content = { progress ->
            Text(text)
        },
        indicator = { progress ->
            SwipeIndicator(
                progress = progress,
                tint = indicatorColor,
                modifier = Modifier.border(1.dp, indicatorColor, CircleShape)
            )
        },
        completedContent = {
            LoadingIndicator(isCompleted, color = indicatorColor)
        }
    )

    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            delay(2000L)
            isCompleted = true
            delay(2000L)
            isCompleted = false
            state.reset()
        }
    }
}

@Composable
private fun SwipeIndicator(
    progress: Float,
    tint: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .clip(CircleShape)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_right_alt),
            contentDescription = "Progressing",
            tint = tint,
            modifier = Modifier.rotate(progress * 360f)
        )
    }
}

@Composable
private fun LoadingIndicator(
    isComplete: Boolean,
    color: Color,
    successColor: Color = Color(0xFF4CAF50)
) {
    AnimatedContent(
        targetState = isComplete,
        modifier = Modifier.fillMaxSize()
    ) { isComplete ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isComplete) {
                Icon(
                    painter = painterResource(Res.drawable.ic_check_circle),
                    contentDescription = "Check",
                    tint = successColor,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                CircularProgressIndicator(
                    color = color,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SwipeButtonFullStylesPreview() {
    val swipeState = rememberSwipeButtonState()
    MaterialTheme {
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
}