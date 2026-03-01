import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import buttons.SwipeComponent

data class Components(
    val component: String,
    val preview: @Composable () -> Unit
)

@Composable
fun ComponentWrapper(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 120.dp)
            .widthIn(max = 640.dp)
            .background(Color.White.copy(0.8f), shape = RoundedCornerShape(12.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        content()
    }
}

val components = listOf(
    Components(
        component = "SwipeButton",
        preview = {
            ComponentWrapper{
                SwipeComponent()
            }
        }
    ),
    Components(
        component = "Swipe",
        preview = {
        }
    )
)