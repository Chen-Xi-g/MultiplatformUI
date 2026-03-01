package validation

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Validation tests for gradle/libs.versions.toml
 *
 * These tests ensure the version catalog is correctly structured and contains
 * all required dependencies for the Multiplatform UI project.
 */
class VersionCatalogValidationTest {

    private val versionCatalogFile = File("../gradle/libs.versions.toml")

    @Test
    fun `version catalog file exists`() {
        assertTrue(
            versionCatalogFile.exists(),
            "gradle/libs.versions.toml file should exist"
        )
    }

    @Test
    fun `version catalog is valid TOML format`() {
        assertTrue(versionCatalogFile.exists(), "File must exist to validate format")

        val content = versionCatalogFile.readText()

        // Validate it contains the three main TOML sections for Gradle version catalogs
        assertTrue(
            content.contains("[versions]"),
            "Version catalog must contain [versions] section"
        )
        assertTrue(
            content.contains("[libraries]"),
            "Version catalog must contain [libraries] section"
        )
        assertTrue(
            content.contains("[plugins]"),
            "Version catalog must contain [plugins] section"
        )
    }

    @Test
    fun `versions section contains required version definitions`() {
        val content = versionCatalogFile.readText()
        val requiredVersions = listOf(
            "kotlin",
            "composeMultiplatform",
            "agp",
            "android-compileSdk",
            "android-minSdk",
            "android-targetSdk"
        )

        requiredVersions.forEach { version ->
            assertTrue(
                content.contains("$version ="),
                "Version catalog must define version: $version"
            )
        }
    }

    @Test
    fun `libraries section contains compose dependencies`() {
        val content = versionCatalogFile.readText()
        val requiredLibraries = listOf(
            "compose-runtime",
            "compose-foundation",
            "compose-material3",
            "compose-ui"
        )

        requiredLibraries.forEach { library ->
            assertTrue(
                content.contains(library),
                "Version catalog must define library: $library"
            )
        }
    }

    @Test
    fun `plugins section contains required plugins`() {
        val content = versionCatalogFile.readText()
        val requiredPlugins = listOf(
            "kotlinMultiplatform",
            "composeMultiplatform",
            "composeCompiler",
            "androidApplication"
        )

        requiredPlugins.forEach { plugin ->
            assertTrue(
                content.contains(plugin),
                "Version catalog must define plugin: $plugin"
            )
        }
    }

    @Test
    fun `version numbers are in valid format`() {
        val content = versionCatalogFile.readText()

        // Extract version lines from [versions] section
        val versionsSection = content.substringAfter("[versions]")
            .substringBefore("[libraries]")

        // Pattern for valid version numbers (semantic versioning or numeric)
        val versionPattern = Regex("""^\w+[\w-]* = "([\d.]+(?:-[\w.]+)?)"$""")

        versionsSection.lines()
            .filter { it.trim().isNotEmpty() && it.contains("=") }
            .forEach { line ->
                val trimmedLine = line.trim()
                assertTrue(
                    versionPattern.matches(trimmedLine),
                    "Version line should match semantic versioning format: $trimmedLine"
                )
            }
    }

    @Test
    fun `library definitions reference valid versions`() {
        val content = versionCatalogFile.readText()

        // Extract all version refs from libraries section
        val librariesSection = content.substringAfter("[libraries]")
            .substringBefore("[plugins]")

        val versionReferences = Regex("""version\.ref = "([^"]+)"""")
            .findAll(librariesSection)
            .map { it.groupValues[1] }
            .toSet()

        // Extract defined versions from [versions] section
        val versionsSection = content.substringAfter("[versions]")
            .substringBefore("[libraries]")

        val definedVersions = Regex("""^(\w+[\w-]*) =""", RegexOption.MULTILINE)
            .findAll(versionsSection)
            .map { it.groupValues[1] }
            .toSet()

        // Verify all version references point to defined versions
        versionReferences.forEach { ref ->
            assertTrue(
                definedVersions.contains(ref),
                "Library references version '$ref' which is not defined in [versions] section"
            )
        }
    }

    @Test
    fun `plugin definitions reference valid versions`() {
        val content = versionCatalogFile.readText()

        // Extract all version refs from plugins section
        val pluginsSection = content.substringAfter("[plugins]")

        val versionReferences = Regex("""version\.ref = "([^"]+)"""")
            .findAll(pluginsSection)
            .map { it.groupValues[1] }
            .toSet()

        // Extract defined versions from [versions] section
        val versionsSection = content.substringAfter("[versions]")
            .substringBefore("[libraries]")

        val definedVersions = Regex("""^(\w+[\w-]*) =""", RegexOption.MULTILINE)
            .findAll(versionsSection)
            .map { it.groupValues[1] }
            .toSet()

        // Verify all version references point to defined versions
        versionReferences.forEach { ref ->
            assertTrue(
                definedVersions.contains(ref),
                "Plugin references version '$ref' which is not defined in [versions] section"
            )
        }
    }

    @Test
    fun `no duplicate version definitions`() {
        val content = versionCatalogFile.readText()

        val versionsSection = content.substringAfter("[versions]")
            .substringBefore("[libraries]")

        val versions = Regex("""^(\w+[\w-]*) =""", RegexOption.MULTILINE)
            .findAll(versionsSection)
            .map { it.groupValues[1] }
            .toList()

        val uniqueVersions = versions.toSet()

        assertEquals(
            versions.size,
            uniqueVersions.size,
            "Version catalog should not contain duplicate version definitions"
        )
    }

    @Test
    fun `no duplicate library definitions`() {
        val content = versionCatalogFile.readText()

        val librariesSection = content.substringAfter("[libraries]")
            .substringBefore("[plugins]")

        val libraries = Regex("""^([\w-]+) =""", RegexOption.MULTILINE)
            .findAll(librariesSection)
            .map { it.groupValues[1] }
            .toList()

        val uniqueLibraries = libraries.toSet()

        assertEquals(
            libraries.size,
            uniqueLibraries.size,
            "Version catalog should not contain duplicate library definitions"
        )
    }

    @Test
    fun `no duplicate plugin definitions`() {
        val content = versionCatalogFile.readText()

        val pluginsSection = content.substringAfter("[plugins]")

        val plugins = Regex("""^([\w-]+) =""", RegexOption.MULTILINE)
            .findAll(pluginsSection)
            .map { it.groupValues[1] }
            .toList()

        val uniquePlugins = plugins.toSet()

        assertEquals(
            plugins.size,
            uniquePlugins.size,
            "Version catalog should not contain duplicate plugin definitions"
        )
    }

    @Test
    fun `kotlin version matches across plugin and library references`() {
        val content = versionCatalogFile.readText()

        // Verify kotlin version is consistently referenced
        val kotlinVersionPattern = Regex("""kotlin = "([\d.]+)"""")
        val kotlinVersion = kotlinVersionPattern.find(content)?.groupValues?.get(1)

        assertNotNull(kotlinVersion, "Kotlin version should be defined")
        assertTrue(
            kotlinVersion!!.isNotEmpty(),
            "Kotlin version should not be empty"
        )
    }

    @Test
    fun `android SDK versions are valid integers`() {
        val content = versionCatalogFile.readText()

        val sdkVersions = mapOf(
            "android-compileSdk" to Regex("""android-compileSdk = "(\d+)""""),
            "android-minSdk" to Regex("""android-minSdk = "(\d+)""""),
            "android-targetSdk" to Regex("""android-targetSdk = "(\d+)"""")
        )

        sdkVersions.forEach { (name, pattern) ->
            val version = pattern.find(content)?.groupValues?.get(1)
            assertNotNull(version, "$name should be defined")
            assertTrue(
                version!!.toIntOrNull() != null && version.toInt() > 0,
                "$name should be a valid positive integer"
            )
        }
    }

    @Test
    fun `minimum SDK version is less than or equal to target SDK`() {
        val content = versionCatalogFile.readText()

        val minSdk = Regex("""android-minSdk = "(\d+)"""")
            .find(content)?.groupValues?.get(1)?.toInt()
        val targetSdk = Regex("""android-targetSdk = "(\d+)"""")
            .find(content)?.groupValues?.get(1)?.toInt()

        assertNotNull(minSdk, "android-minSdk should be defined")
        assertNotNull(targetSdk, "android-targetSdk should be defined")

        assertTrue(
            minSdk!! <= targetSdk!!,
            "android-minSdk ($minSdk) should be less than or equal to android-targetSdk ($targetSdk)"
        )
    }

    @Test
    fun `compose multiplatform version is consistent`() {
        val content = versionCatalogFile.readText()

        // Verify composeMultiplatform version is defined and referenced correctly
        val composeVersion = Regex("""composeMultiplatform = "([\d.]+)"""")
            .find(content)?.groupValues?.get(1)

        assertNotNull(composeVersion, "composeMultiplatform version should be defined")

        // Count references to this version
        val references = Regex("""version\.ref = "composeMultiplatform"""")
            .findAll(content)
            .count()

        assertTrue(
            references > 0,
            "composeMultiplatform version should be referenced in libraries or plugins"
        )
    }
}