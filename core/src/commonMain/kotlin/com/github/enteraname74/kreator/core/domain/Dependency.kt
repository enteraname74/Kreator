package com.github.enteraname74.kreator.core.domain

import com.github.enteraname74.kreator.core.data.Plugins

/**
 * Represent a Dependency (plugin or library) to add to libs.versions.toml
 */
sealed class Dependency {
    abstract val name: String
    abstract val targets: List<Target>

    data class Plugin(
        override val name: String,
        val version: Version?,
        val mode: Mode = Mode.Alias,
        val id: String,
    ): Dependency() {
        override val targets: List<Target> = emptyList()
        val tomlPath: String
            get() = "libs.plugins.${name.replace("-",".")}"

        fun usePlugin(): String =
            when(mode) {
                Mode.Alias -> "alias($tomlPath)"
                Mode.Id -> """id("$id")"""
                Mode.Kotlin -> """kotlin("$id")"""
            }

        fun tomlDeclaration(): String =
            version?.let {
                """$name = { id = "$id", version.ref = "${version.name}" }"""
            } ?: ""

        enum class Mode {
            Alias,
            Id,
            Kotlin,
        }
    }

    data class Library(
        override val name: String,
        override val targets: List<Target>,
        val module: String,
        val version: Version,
        val bundleName: String? = null,
    ): Implementable() {
        val tomlPath: String
            get() = "libs.${name.replace("-",".")}"

        /**
         * Library implementation in a build.gradle.kts file
         */
        override fun implementation(): String =
            "implementation($tomlPath)"

        /**
         * Library api implementation in a build.gradle.kts file
         */
        fun api(): String =
            "api($tomlPath)"

        override fun toString(): String =
            """$name = { module = "$module", version.ref = "${version.name}" }"""
    }

    data class Module(
        override val name: String,
        override val targets: List<Target>,
    ): Implementable() {
        override fun implementation(): String =
            """implementation(project(":$name"))"""
    }

    data class Version(
        val name: String,
        val version: String,
    ) {
        override fun toString(): String =
            """$name = "$version""""
    }

    abstract class Implementable: Dependency() {
        abstract fun implementation(): String
    }
}

fun List<Dependency.Library>.toBundle(bundleName: String): String =
    this.joinToString(
        separator = ", ",
        prefix = "$bundleName = [",
        postfix = "]",
    ) { dependency ->
        """"${dependency.name}""""
    }

fun List<Dependency.Plugin>.hasAndroidPlugin(): Boolean =
    any { it.name == Plugins.ANDROID_LIBRARY.name || it.name == Plugins.ANDROID_APPLICATION.name }
