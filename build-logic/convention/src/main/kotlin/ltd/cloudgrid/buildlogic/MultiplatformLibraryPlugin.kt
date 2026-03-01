package ltd.cloudgrid.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.Actions.with
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val catalogLibs = libs
            with(pluginManager) {
                apply(catalogLibs.findPlugin("androidKotlinMultiplatformLibrary").toPluginId())
                apply(catalogLibs.findPlugin("kotlinMultiplatform").toPluginId())
                apply(catalogLibs.findPlugin("composeMultiplatform").toPluginId())
                apply(catalogLibs.findPlugin("composeCompiler").toPluginId())
                apply(catalogLibs.findPlugin("dokka").toPluginId())
            }

            extensions.configure<KotlinMultiplatformExtension>(::configureMultiplatformLibrary)

            dependencies {
                "androidRuntimeClasspath"(catalogLibs.findLibrary("androidx.compose.ui.tooling").get())
            }
        }
    }
}