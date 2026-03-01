package ltd.cloudgrid.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.plugin.use.PluginDependency
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.util.Optional

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Optional<VersionConstraint>.toInt() = get().requiredVersion.toInt()
fun Optional<Provider<PluginDependency>>.toPluginId() = get().get().pluginId

@OptIn(ExperimentalKotlinGradlePluginApi::class)
internal fun Project.configureMultiplatformLibrary(
    extension: KotlinMultiplatformExtension
) = extension.apply {

    val xcfName = name

    group = "ltd.cloudgrid.components"
    version = libs.findVersion("mavenVersion").get().toString()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    dependencies {
        implementation(libs.findLibrary("compose.runtime").get())
        implementation(libs.findLibrary("compose.foundation").get())
        implementation(libs.findLibrary("compose.material3").get())
        implementation(libs.findLibrary("compose.ui").get())
        implementation(libs.findLibrary("compose.components.resources").get())
        implementation(libs.findLibrary("compose.uiToolingPreview").get())
        implementation(libs.findLibrary("androidx.lifecycle.viewmodelCompose").get())
        implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
    }
}