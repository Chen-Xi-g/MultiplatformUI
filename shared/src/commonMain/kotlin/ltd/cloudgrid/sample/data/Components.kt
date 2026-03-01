package ltd.cloudgrid.sample.data

import ltd.cloudgrid.sample.model.ComponentItem
import ltd.cloudgrid.sample.nav.Buttons
import ltd.cloudgrid.sample.nav.SwipeButton
import ltd.cloudgrid.sample.screens.ComponentsScreen
import ltd.cloudgrid.sample.screens.buttons.SwipeButtonScreen

val components = listOf(
    ComponentItem(
        name = "Buttons",
        navKey = Buttons,
        child = listOf(
            ComponentItem(
                name = "Swipe Button",
                navKey = SwipeButton,
                content = { backStack, child ->
                    SwipeButtonScreen()
                }
            )
        ),
        content = { backStack, child ->
            ComponentsScreen(
                list = child,
                onItemClick = {
                    backStack.add(it.navKey)
                }
            )
        }
    )
)