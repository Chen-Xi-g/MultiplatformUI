package ltd.cloudgrid.sample.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data object Buttons: NavKey

@Serializable
data object SwipeButton: NavKey