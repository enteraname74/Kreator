group = "com.github.enteraname74.kreator"
version = "0.1.0"

repositories {
    mavenCentral()
}

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
}