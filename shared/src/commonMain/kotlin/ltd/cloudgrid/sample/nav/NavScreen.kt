package ltd.cloudgrid.sample.nav

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import androidx.navigationevent.compose.rememberNavigationEventDispatcherOwner
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ltd.cloudgrid.sample.data.components
import ltd.cloudgrid.sample.screens.ComponentsScreen
import ltd.cloudgrid.sample.screens.buttons.SwipeButtonScreen
import ltd.cloudgrid.sample.utils.executeChildContent
import ltd.cloudgrid.sample.utils.executeContent
import ltd.cloudgrid.sample.utils.navTitle

private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Home::class, Home.serializer())
            subclass(Buttons::class, Buttons.serializer())
            subclass(SwipeButton::class, SwipeButton.serializer())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavBackStack() {
    val backStack = rememberNavBackStack(config, Home)
    val listDetailStrategy = rememberListDetailSceneStrategy<Any>()
    val components by remember { mutableStateOf(components) }
    val title = components.navTitle(backStack)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title
                    )
                },
                navigationIcon = {
                    if (backStack.size > 1) {
                        IconButton(
                            onClick = {
                                backStack.removeLastOrNull()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets.systemBars.exclude(WindowInsets.navigationBars)
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            sceneStrategy = listDetailStrategy,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            transitionSpec = {
                slideInHorizontally(initialOffsetX = { it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { -it })
            },
            popTransitionSpec = {
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            },
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<Home>(
                    metadata = ListDetailSceneStrategy.listPane(
                        detailPlaceholder = {
                            Box(Modifier.fillMaxSize())
                        }
                    )
                ) {
                    ComponentsScreen(
                        list = components,
                        onItemClick = {
                            backStack.add(it.navKey)
                        }
                    )
                }
                entry<Buttons>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) {
                    components.executeContent(Buttons::class)?.let {
                        it.content(backStack, it.child)
                    }
                }
                entry<SwipeButton>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) {
                    components.executeChildContent(Buttons::class, SwipeButton::class)?.let {
                        it.content(backStack, it.child)
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun NavScreenPreview() {
    MaterialTheme {
        val navigationEventDispatcherOwner = rememberNavigationEventDispatcherOwner(parent = null)
        CompositionLocalProvider(
            LocalNavigationEventDispatcherOwner provides navigationEventDispatcherOwner
        ) {
            NavBackStack()
        }
    }
}