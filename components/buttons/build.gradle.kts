import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins{
    id("multiplatform.library")
    id("maven.publish")
}

kotlin {
    androidLibrary {
        namespace = "ltd.cloudgrid.buttons"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
        androidResources {
            enable = true
        }
    }
}