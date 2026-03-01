package validation

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Validation tests for README.md
 *
 * These tests ensure the README is properly structured and contains
 * all required documentation sections for the Multiplatform UI project.
 */
class ReadmeValidationTest {

    private val readmeFile = File("../README.md")

    @Test
    fun `README file exists`() {
        assertTrue(
            readmeFile.exists(),
            "README.md file should exist in the project root"
        )
    }

    @Test
    fun `README is not empty`() {
        assertTrue(readmeFile.exists(), "File must exist to validate content")

        val content = readmeFile.readText()
        assertTrue(
            content.isNotBlank(),
            "README.md should not be empty"
        )
        assertTrue(
            content.length > 100,
            "README.md should contain substantial content"
        )
    }

    @Test
    fun `README contains project title`() {
        val content = readmeFile.readText()

        assertTrue(
            content.contains("Multiplatform UI", ignoreCase = true),
            "README should contain the project title 'Multiplatform UI'"
        )
    }

    @Test
    fun `README contains required sections`() {
        val content = readmeFile.readText()

        val requiredSections = listOf(
            "Documentation",
            "Project Structure",
            "Component Library Integration",
            "License"
        )

        requiredSections.forEach { section ->
            assertTrue(
                content.contains(section, ignoreCase = false),
                "README should contain section: $section"
            )
        }
    }

    @Test
    fun `README contains platform badges`() {
        val content = readmeFile.readText()

        val platforms = listOf("Android", "iOS", "Desktop", "Web")

        platforms.forEach { platform ->
            assertTrue(
                content.contains(platform),
                "README should mention platform: $platform"
            )
        }
    }

    @Test
    fun `README contains documentation links`() {
        val content = readmeFile.readText()

        // Check for markdown link format
        val linkPattern = Regex("""\[([^\]]+)\]\(([^)]+)\)""")
        val links = linkPattern.findAll(content).toList()

        assertTrue(
            links.isNotEmpty(),
            "README should contain at least one markdown link"
        )

        // Verify specific documentation links exist
        assertTrue(
            content.contains("https://chen-xi-g.github.io/MultiplatformUI/"),
            "README should contain link to full documentation"
        )
        assertTrue(
            content.contains("https://chen-xi-g.github.io/MultiplatformUI/dokka/"),
            "README should contain link to API reference (Dokka)"
        )
    }

    @Test
    fun `README contains supported platforms section`() {
        val content = readmeFile.readText()

        assertTrue(
            content.contains("Supported Platforms", ignoreCase = true),
            "README should contain 'Supported Platforms' section"
        )
    }

    @Test
    fun `README contains version badges`() {
        val content = readmeFile.readText()

        // Check for Maven Central badge
        assertTrue(
            content.contains("maven-central", ignoreCase = true) ||
                    content.contains("Maven Central", ignoreCase = false),
            "README should contain Maven Central badge"
        )

        // Check for Kotlin version badge
        assertTrue(
            content.contains("kotlin", ignoreCase = true) &&
                    content.contains("badge", ignoreCase = true),
            "README should contain Kotlin version badge"
        )

        // Check for Compose Multiplatform badge
        assertTrue(
            content.contains("compose", ignoreCase = true),
            "README should mention Compose Multiplatform"
        )
    }

    @Test
    fun `README contains license information`() {
        val content = readmeFile.readText()

        assertTrue(
            content.contains("License", ignoreCase = false),
            "README should contain license section"
        )
        assertTrue(
            content.contains("MIT License") || content.contains("LICENSE"),
            "README should reference the MIT License or LICENSE file"
        )
    }

    @Test
    fun `README project structure is documented`() {
        val content = readmeFile.readText()

        val expectedModules = listOf(
            "shared",
            "app",
            "androidApp",
            "iosApp",
            "desktopApp",
            "webApp",
            "components"
        )

        expectedModules.forEach { module ->
            assertTrue(
                content.contains(module),
                "README should document module: $module"
            )
        }
    }

    @Test
    fun `README contains component library information`() {
        val content = readmeFile.readText()

        assertTrue(
            content.contains("Component Library", ignoreCase = true) ||
                    content.contains("components", ignoreCase = true),
            "README should contain component library information"
        )
    }

    @Test
    fun `README mentions Compose Multiplatform`() {
        val content = readmeFile.readText()

        assertTrue(
            content.contains("Compose Multiplatform"),
            "README should mention Compose Multiplatform as the foundation"
        )
    }

    @Test
    fun `README markdown headers are properly formatted`() {
        val content = readmeFile.readText()

        // Check for markdown headers (lines starting with #)
        val headerPattern = Regex("""^#{1,6}\s+.+$""", RegexOption.MULTILINE)
        val headers = headerPattern.findAll(content).toList()

        assertTrue(
            headers.isNotEmpty(),
            "README should contain markdown headers"
        )

        // Verify headers have proper spacing after #
        headers.forEach { match ->
            val header = match.value
            assertTrue(
                Regex("""^#{1,6}\s+""").containsMatchIn(header),
                "Markdown headers should have space after # symbols: $header"
            )
        }
    }

    @Test
    fun `README contains currently supported components list`() {
        val content = readmeFile.readText()

        assertTrue(
            content.contains("Currently Supported Components", ignoreCase = true) ||
                    content.contains("Components:", ignoreCase = true),
            "README should list currently supported components"
        )

        // Check for at least one documented component
        assertTrue(
            content.contains("SwipeButton") || content.contains("Button"),
            "README should document at least one component"
        )
    }

    @Test
    fun `README table structure is valid`() {
        val content = readmeFile.readText()

        // Find markdown tables
        val tablePattern = Regex("""^\|.+\|$""", RegexOption.MULTILINE)
        val tableLines = tablePattern.findAll(content).toList()

        if (tableLines.isNotEmpty()) {
            // If tables exist, verify they have consistent column counts
            val firstTableLine = tableLines.first().value
            val columnCount = firstTableLine.split("|").size

            assertTrue(
                columnCount > 2, // At least 2 columns (split creates empty strings at start/end)
                "Tables should have at least one meaningful column"
            )
        }
    }

    @Test
    fun `README links are properly formatted`() {
        val content = readmeFile.readText()

        // Extract all markdown links
        val linkPattern = Regex("""\[([^\]]+)\]\(([^)]+)\)""")
        val links = linkPattern.findAll(content).map { it.groupValues[2] }.toList()

        links.forEach { link ->
            // Verify links don't contain obvious formatting errors
            assertTrue(
                link.isNotBlank(),
                "Link URLs should not be empty"
            )
            assertTrue(
                !link.contains(" ") || link.startsWith("http"),
                "Link URLs should not contain unencoded spaces: $link"
            )
        }
    }

    @Test
    fun `README contains meaningful description`() {
        val content = readmeFile.readText()

        // Should contain description of what the library does
        assertTrue(
            content.contains("UI component library", ignoreCase = true) ||
                    content.contains("component library", ignoreCase = true),
            "README should describe the project as a UI component library"
        )

        assertTrue(
            content.contains("advanced components", ignoreCase = true) ||
                    content.contains("components", ignoreCase = true),
            "README should mention that it provides components"
        )
    }

    @Test
    fun `README image references are valid markdown`() {
        val content = readmeFile.readText()

        // Check for markdown image syntax
        val imagePattern = Regex("""!\[([^\]]*)\]\(([^)]+)\)""")
        val images = imagePattern.findAll(content).toList()

        images.forEach { match ->
            val imagePath = match.groupValues[2]
            assertTrue(
                imagePath.isNotBlank(),
                "Image paths should not be empty"
            )
            // Image paths should either be URLs or relative paths
            assertTrue(
                imagePath.startsWith("http") ||
                        imagePath.startsWith("docs/") ||
                        imagePath.startsWith("./") ||
                        imagePath.startsWith("/") ||
                        !imagePath.contains(" "),
                "Image path should be a valid URL or path: $imagePath"
            )
        }
    }

    @Test
    fun `README contains star history chart`() {
        val content = readmeFile.readText()

        assertTrue(
            content.contains("Star History", ignoreCase = true),
            "README should contain Star History section"
        )
    }

    @Test
    fun `README uses consistent heading levels`() {
        val content = readmeFile.readText()

        // Extract all headers
        val headerPattern = Regex("""^(#{1,6})\s+(.+)$""", RegexOption.MULTILINE)
        val headers = headerPattern.findAll(content).toList()

        assertTrue(
            headers.isNotEmpty(),
            "README should contain headers"
        )

        // Check that we have at least one h2 or h3 header (common for README sections)
        val hasSubHeaders = headers.any {
            val level = it.groupValues[1].length
            level >= 2
        }

        assertTrue(
            hasSubHeaders,
            "README should use multiple heading levels for organization"
        )
    }

    @Test
    fun `README important notice is properly formatted`() {
        val content = readmeFile.readText()

        // Check for important notice using markdown alert syntax
        assertTrue(
            content.contains("> [!IMPORTANT]") ||
                    content.contains("IMPORTANT", ignoreCase = true),
            "README should contain important project notices"
        )
    }
}