package ltd.cloudgrid.sample.model

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

data class ComponentItem(
    val name: String,
    val navKey: NavKey,
    val child: List<ComponentItem> = emptyList(),
    val content: @Composable (NavBackStack<NavKey>, List<ComponentItem>) -> Unit = {_, _ ->},
)
