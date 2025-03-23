package com.github.enteraname74.kreator.core.data

import com.github.enteraname74.kreator.core.domain.Dependency

object Plugins {
    val ANDROID_APPLICATION = Dependency.Plugin(
        name = "android-application",
        version = Versions.AGP,
        id = "com.android.application",
    )
    val ANDROID_LIBRARY = Dependency.Plugin(
        name = "android-library",
        version = Versions.AGP,
        id = "com.android.library",
    )
    val JETBRAINS_COMPOSE = Dependency.Plugin(
        name = "jetbrains-compose",
        version = Versions.COMPOSE,
        id = "org.jetbrains.compose",
    )
    val KOTLIN_MULTIPLATFORM = Dependency.Plugin(
        name = "kotlin-multiplatform",
        version = Versions.KOTLIN,
        id = "org.jetbrains.kotlin.multiplatform",
    )
    val COMPOSE_COMPILER = Dependency.Plugin(
        name = "compose-compiler",
        version = Versions.KOTLIN,
        id = "org.jetbrains.kotlin.plugin.compose",
    )
    val KOTLIN_SERIALIZATION = Dependency.Plugin(
        name = "kotlin-serialization",
        version = Versions.KOTLIN,
        id = "org.jetbrains.kotlin.plugin.serialization",
    )
}