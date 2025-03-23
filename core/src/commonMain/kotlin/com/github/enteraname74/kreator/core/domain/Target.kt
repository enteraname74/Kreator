package com.github.enteraname74.kreator.core.domain

import com.github.enteraname74.kreator.core.utils.BlockBuilder

/**
 * Kotlin Multiplatform target to add to the build.gradle.kts file of a module.
 */
sealed interface Target {
    fun block(
        blockBuilder: BlockBuilder,
        contentBuilder: BlockBuilder.() -> Unit,
    )

    data class Android(
        val compileSdk: Int,
        val targetSdk: Int,
        val minSdk: Int,
        val buildTypes: List<BuildType>,
    ): Target {
        override fun block(
            blockBuilder: BlockBuilder,
            contentBuilder: BlockBuilder.() -> Unit,
        ) {
            blockBuilder.subBlock("androidMain.dependencies") {
                contentBuilder()
            }
        }

        override fun toString(): String =
            "androidTarget()"
    }

    object Desktop: Target {
        override fun block(
            blockBuilder: BlockBuilder,
            contentBuilder: BlockBuilder.() -> Unit,
        ) {
            blockBuilder.subBlock("val desktopMain by getting") {
                subBlock("dependencies") {
                    contentBuilder()
                }
            }
        }

        override fun toString(): String =
            """jvm("desktop")"""
    }
}

fun List<Target>.hasAndroidTarget(): Boolean =
    any { it is Target.Android }

fun List<Target>.hasDesktopTarget(): Boolean =
    any { it is Target.Desktop }