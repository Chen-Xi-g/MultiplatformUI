plugins {
    `kotlin-dsl`
}

group = "ltd.cloudgrid.buildlogic"

dependencies {
    compileOnly(libs.vanniktechMavenPublish)
    compileOnly(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        register("multiplatformLibrary") {
            id = "multiplatform.library"
            implementationClass = "ltd.cloudgrid.buildlogic.MultiplatformLibraryPlugin"
        }
        register("mavenPublish") {
            id = "maven.publish"
            implementationClass = "ltd.cloudgrid.buildlogic.MavenPublishPlugin"
        }
    }
}