import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.w3c.dom.url.URLSearchParams

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val params = URLSearchParams(document.location?.search)
    val component = params.get("component")
    ComposeViewport {
        Sample(component = component)
    }
}