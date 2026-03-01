package ltd.cloudgrid.comments.buttons.swipe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * In accordance with the Compose state management pattern, this class encapsulates
 * the physical interaction logic (dragging, progress, and thresholds) while remaining
 * agnostic of the visual styling.
 *
 * @param confirmThreshold The normalized value (0.0 to 1.0) that the user must drag
 * beyond to trigger the completion state. Defaults to 0.9 (90%).
 * @param onComplete Callback invoked when the button successfully reaches the completed state.
 * @param onReset Callback invoked when the button is reset from completed to idle.
 * @param onProgressChanged Callback providing real-time updates of the drag progress (0.0 to 1.0).
 */
@Stable
class SwipeButtonState(
    val confirmThreshold: Float = 0.9f,
    private val onComplete: () -> Unit = {},
    private val onReset: () -> Unit = {},
    private val onProgressChanged: (Float) -> Unit = {}
) {
    /**
     * Whether the swipe action has been successfully completed.
     * When true, the button typically transforms into a loading or success indicator.
     */
    var isCompleted by mutableStateOf(false)
        private set

    /**
     * Indicates whether the user is currently interacting with the slider.
     */
    var isDragging by mutableStateOf(false)
        private set

    /**
     * The internal animatable progress of the swipe (0f = start, 1f = end).
     */
    val swipeProgress = Animatable(0f)

    /**
     * Updates the current swipe progress instantly.
     * @param value The new progress value, which will be coerced between 0.0 and 1.0.
     */
    suspend fun updateProgress(value: Float) {
        val clamped = value.coerceIn(0f, 1f)
        swipeProgress.snapTo(clamped)
        onProgressChanged(clamped)
        isDragging = clamped > 0f && clamped < 1f && !isCompleted
    }

    /**
     * Animates the slider back to the start position.
     * Usually called when the user releases the drag before reaching the [confirmThreshold].
     */
    suspend fun animateToRest(animationSpec: AnimationSpec<Float> = spring(stiffness = Spring.StiffnessLow)) {
        swipeProgress.animateTo(0f, animationSpec)
        isDragging = false
    }

    /**
     * Transitions the state to completed and animates the progress to 100%.
     */
    suspend fun complete(animationSpec: AnimationSpec<Float> = tween(300)) {
        isCompleted = true
        swipeProgress.animateTo(1f, animationSpec)
        onComplete()
        isDragging = false
    }

    /**
     * Resets the button to its initial idle state with an animation.
     */
    suspend fun reset(animationSpec: AnimationSpec<Float> = tween(300)) {
        isCompleted = false
        swipeProgress.animateTo(0f, animationSpec)
        onReset()
        isDragging = false
    }

    /**
     * Immediately sets the state to completed without playing an animation.
     */
    suspend fun forceComplete() {
        isCompleted = true
        swipeProgress.snapTo(1f)
        isDragging = false
    }

    /**
     * Immediately resets the state to idle without playing an animation.
     */
    suspend fun forceReset() {
        isCompleted = false
        swipeProgress.snapTo(0f)
        isDragging = false
    }
}

/**
 * Creates and remembers a [SwipeButtonState] across recompositions.
 *
 * @param confirmThreshold The threshold (0.0 to 1.0) required to trigger completion.
 * @param onComplete Callback for completion events.
 * @param onReset Callback for reset events.
 * @param onProgressChanged Callback for tracking drag progress.
 */
@Composable
fun rememberSwipeButtonState(
    confirmThreshold: Float = 0.9f,
    onComplete: () -> Unit = {},
    onReset: () -> Unit = {},
    onProgressChanged: (Float) -> Unit = {}
): SwipeButtonState {
    return remember(confirmThreshold) {
        SwipeButtonState(confirmThreshold, onComplete, onReset, onProgressChanged)
    }
}