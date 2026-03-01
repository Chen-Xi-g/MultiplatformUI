package ltd.cloudgrid.comments.buttons.swipe

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


/**
 * This component features smooth animations, state management, and transitions between
 * idle and completed states (where the button collapses into a circular progress indicator).
 *
 * @param modifier The [Modifier] to be applied to the root layout.
 * @param state The state object [SwipeButtonState] to control and observe the button's progress.
 * @param isEnabled Whether the button is interactive. If false, drag gestures are disabled.
 * @param isContentFollow Whether the background content should translate horizontally as the user swipes.
 * @param cornerRadius The initial corner radius of the button container.
 * @param contentAlphaFactor Controls how quickly the background content fades out during the swipe.
 * @param widthAnimationSpec Animation spec for the width transition when reaching the completed state.
 * @param cornerAnimationSpec Animation spec for the corner radius transition.
 * @param restAnimationSpec Animation spec for the "spring back" effect when a swipe is released before the threshold.
 * @param completeAnimationSpec Animation spec for the final progress movement to 100%.
 * @param resetAnimationSpec Animation spec for resetting the button state.
 * @param background Composable for the button's background layer.
 * @param content Composable for the main text or instruction, providing the current swipe progress (0f-1f).
 * @param indicator Composable for the draggable handle, providing the current swipe progress.
 * @param completedContent Composable displayed when the button is in the [SwipeButtonState.isCompleted] state.
 */
@Composable
fun SwipeButton(
    modifier: Modifier = Modifier,
    state: SwipeButtonState = rememberSwipeButtonState(),
    isEnabled: Boolean = true,
    isContentFollow: Boolean = true,
    cornerRadius: Dp = 12.dp,
    contentAlphaFactor: Float = 1.5f,
    widthAnimationSpec: AnimationSpec<Dp> = tween(500),
    cornerAnimationSpec: AnimationSpec<Dp> = tween(500),
    restAnimationSpec: AnimationSpec<Float> = spring(stiffness = Spring.StiffnessLow),
    completeAnimationSpec: AnimationSpec<Float> = tween(300),
    resetAnimationSpec: AnimationSpec<Float> = tween(300),
    background: @Composable (enabled: Boolean) -> Unit = {},
    content: @Composable (progress: Float) -> Unit,
    indicator: @Composable (progress: Float) -> Unit,
    completedContent: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = this.maxWidth
        val maxHeight = this.maxHeight
        var isInitialized by remember { mutableStateOf(false) }

        LaunchedEffect(maxWidth, maxHeight) {
            if (maxWidth > 0.dp && maxHeight > 0.dp) {
                isInitialized = true
            }
        }

        val animatedWidth by animateDpAsState(
            targetValue = if (state.isCompleted) maxHeight else maxWidth,
            animationSpec = if (isInitialized && isEnabled) widthAnimationSpec else snap(),
            label = "swipeButtonWidth",
            finishedListener = {
                scope.launch {
                    state.swipeProgress.snapTo(0f)
                }
            }
        )

        val animatedCornerRadius by animateDpAsState(
            targetValue = if (state.isCompleted) maxHeight / 2 else cornerRadius,
            animationSpec = if (isInitialized && isEnabled) cornerAnimationSpec else snap(),
            label = "swipeButtonCornerRadius"
        )

        Box(
            modifier = Modifier
                .width(animatedWidth)
                .height(maxHeight)
                .clip(RoundedCornerShape(animatedCornerRadius)),
            contentAlignment = Alignment.CenterStart
        ) {
            val maxTravel = remember(animatedWidth, maxHeight) {
                val currentWidthPx = with(density) { animatedWidth.toPx() }
                val indicatorWidthPx = with(density) { maxHeight.toPx() }
                (currentWidthPx - indicatorWidthPx).coerceAtLeast(0f)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                background(isEnabled)
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationX =
                            if (isContentFollow && isEnabled) state.swipeProgress.value * maxTravel else 0f
                        alpha = if (isEnabled) {
                            1f - (state.swipeProgress.value * contentAlphaFactor).coerceAtMost(1f)
                        } else {
                            0.7f
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (!state.isCompleted) {
                    content(state.swipeProgress.value)
                }
            }

            if (!state.isCompleted && isEnabled) {
                Box(
                    modifier = Modifier
                        .offset {
                            val offsetX =
                                if (state.swipeProgress.value <= 0.01f || !isInitialized) {
                                    0
                                } else {
                                    (state.swipeProgress.value * maxTravel).toInt()
                                }
                            IntOffset(offsetX, 0)
                        }
                        .size(maxHeight)
                        .pointerInput(maxTravel, maxTravel, state.isCompleted) {
                            if (!isEnabled || maxTravel <= 0f) return@pointerInput
                            detectHorizontalDragGestures(
                                onDragEnd = {
                                    scope.launch {
                                        if (state.swipeProgress.value >= state.confirmThreshold) {
                                            state.complete(completeAnimationSpec)
                                        } else {
                                            state.animateToRest(restAnimationSpec)
                                        }
                                    }
                                },
                                onHorizontalDrag = { change, dragAmount ->
                                    change.consume()
                                    scope.launch {
                                        val newProgress =
                                            state.swipeProgress.value + (dragAmount / maxTravel)
                                        state.updateProgress(newProgress)
                                    }
                                }
                            )
                        }
                ) {
                    indicator(state.swipeProgress.value)
                }
            }

            if (state.isCompleted) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    completedContent()
                }
            }
        }
    }
}