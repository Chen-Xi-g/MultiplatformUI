plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnitPlatform()
}