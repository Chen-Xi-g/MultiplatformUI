package ltd.cloudgrid.sample.utils

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import ltd.cloudgrid.sample.data.components
import ltd.cloudgrid.sample.model.ComponentItem
import ltd.cloudgrid.sample.nav.Buttons
import ltd.cloudgrid.sample.nav.SwipeButton
import kotlin.reflect.KClass

fun List<ComponentItem>.navTitle(backStack: NavBackStack<NavKey>): String{
    return find { it.navKey == backStack.lastOrNull() }?.name ?: find { it.navKey == backStack.getOrNull(backStack.size - 2) }?.child?.find { it.navKey == backStack.lastOrNull() }?.name ?: "Recipes"
}
fun <T: NavKey> List<ComponentItem>.executeContent(navKeyType: KClass<T>): ComponentItem?{
    return this.find { navKeyType.isInstance(it.navKey) }
}

fun <T: NavKey, U: NavKey> List<ComponentItem>.executeChildContent(navKeyType: KClass<T>, childNavKeyType: KClass<U>): ComponentItem?{
    return executeContent(navKeyType)?.child?.find { childNavKeyType.isInstance(it.navKey) }
}