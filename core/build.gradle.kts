plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

group = "com.github.enteraname74.simplecolorthemegenerator"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(17)
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
        }
    }
}