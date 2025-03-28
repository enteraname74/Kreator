package com.github.enteraname74.kreator.core.data

import com.github.enteraname74.kreator.core.domain.Dependency

object Plugins {
    val ANDROID_APPLICATION = Dependency.Plugin(
        name = "android-application",
        version = Versions.AGP,
        mode = Dependency.Plugin.Mode.Id,
        id = "com.android.application",
    )
    val ANDROID_LIBRARY = Dependency.Plugin(
        name = "android-library",
        version = Versions.AGP,
        mode = Dependency.Plugin.Mode.Id,
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
    val KAPT = Dependency.Plugin(
        name = "kapt",
        version = null,
        id = "kapt",
        mode = Dependency.Plugin.Mode.Kotlin,
    )

    val ALL: List<Dependency.Plugin> = listOf(
        ANDROID_APPLICATION,
        ANDROID_LIBRARY,
        JETBRAINS_COMPOSE,
        KOTLIN_MULTIPLATFORM,
        COMPOSE_COMPILER,
        KOTLIN_SERIALIZATION,
        KAPT,
    )
}