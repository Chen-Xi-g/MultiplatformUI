package ltd.cloudgrid.buildlogic

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.internal.Actions
import org.gradle.kotlin.dsl.configure
import org.gradle.plugins.signing.SigningExtension
import kotlin.jvm.java

class MavenPublishPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        Actions.with(target) {
            val catalogLibs = libs
            val mavenGroupId = "ltd.cloudgrid.components"
            val mavenArtifactId = project.name
            val mavenVersion = libs.findVersion("mavenVersion").get().toString()
            val localPackageUrl = layout.buildDirectory.dir("maven/repos/local")
            val githubPackagesUrl = "https://maven.pkg.github.com/Chen-Xi-g/MultiplatformUI"
            with(pluginManager) {
                apply(catalogLibs.findPlugin("vanniktechMavenPublish").toPluginId())
            }
            extensions.configure<MavenPublishBaseExtension>{
                publishToMavenCentral()
                signAllPublications()

                configure(KotlinMultiplatform())
                coordinates(mavenGroupId, mavenArtifactId, mavenVersion)

                pom {
                    info(mavenArtifactId)
                }
            }
            extensions.configure<PublishingExtension>{
                group = mavenGroupId
                version = mavenVersion
                publications.withType(MavenPublication::class.java) {
                    pom {
                        info(mavenArtifactId)
                    }
                    repositories{
                        maven {
                            name = "local"
                            url = uri(localPackageUrl)
                        }
                        maven {
                            name = "github"
                            url = uri(githubPackagesUrl)
                            credentials {
                                username = System.getenv("GITHUB_ACTOR")
                                password = System.getenv("GITHUB_TOKEN")
                            }
                        }
                    }
                }
                extensions.configure<SigningExtension>{
                    setRequired({ gradle.taskGraph.hasTask("publish") })
                    sign(publications)
                }
            }
        }
    }
}

private fun MavenPom.info(mavenArtifactId: String){
    name.set(mavenArtifactId)
    description.set(
        when(mavenArtifactId){
            "buttons" -> "A button that supports swipe-to-confirm operations, commonly used for payments or important operation confirmations."
            else ->  "`Multiplatform UI` is an open-source UI component library built on `Compose Multiplatform`."
        }
    )
    inceptionYear.set("2026")
    url.set("https://github.com/Chen-Xi-g/MultiplatformUI")

    licenses {
        license {
            name.set("MIT")
            url.set("https://github.com/Chen-Xi-g/MultiplatformUI/blob/main/LICENSE")
        }
    }

    developers {
        developer {
            id.set("cloud-grid")
            name.set("Cloud Grid")
            email.set("a912816369@gmail.com")
            url.set("https://ggf.yiqg.com/")
        }
    }

    scm {
        url.set("https://github.com/Chen-Xi-g/MultiplatformUI")
        connection.set("scm:git:https://github.com/Chen-Xi-g/MultiplatformUI.git")
        developerConnection.set("scm:git:https://git@github.com/Chen-Xi-g/MultiplatformUI.git")
    }

    ciManagement {
        system.set("Github Actions")
        url.set("https://github.com/Chen-Xi-g/MultiplatformUI/actions")
    }
}