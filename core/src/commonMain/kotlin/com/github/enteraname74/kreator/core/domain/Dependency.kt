package com.github.enteraname74.kreator.core.domain

/**
 * Represent a Dependency (plugin or library) to add to libs.versions.toml
 */
sealed class Dependency {
    abstract val name: String
    abstract val version: Version
    abstract val targets: List<Target>
    abstract val tomlPath: String

    data class Plugin(
        override val name: String,
        override val version: Version,
        val canUseAlias: Boolean = true,
        val id: String,
    ): Dependency() {
        override val targets: List<Target> = emptyList()
        
        override val tomlPath: String
            get() = "libs.plugins.${name.replace("-",".")}"

        /**
         * Alias use of the plugin in a build.gradle.kts file
         */
        private fun alias(): String =
            "alias($tomlPath)"

        /**
         * Id use of the plugin in a build.gradle.kts file
         */
        private fun id(): String =
            "id($name)"

        fun usePlugin(): String =
            if (canUseAlias) alias() else id()

        override fun toString(): String =
            """$name = { id = "$id", version.ref = "${version.name}" }"""
    }

    data class Library(
        override val name: String,
        override val version: Version,
        override val targets: List<Target>,
        val module: String,
        val bundleName: String? = null,
    ): Dependency() {
        override val tomlPath: String
            get() = "libs.${name.replace("-",".")}"

        /**
         * Library implementation in a build.gradle.kts file
         */
        fun implementation(): String =
            "implementation($tomlPath)"

        /**
         * Library api implementation in a build.gradle.kts file
         */
        fun api(): String =
            "api($tomlPath)"

        override fun toString(): String =
            """$name = { module = "$module", version.ref = "${version.name}" }"""
    }

    data class Version(
        val name: String,
        val version: String,
    ) {
        override fun toString(): String =
            """$name = "$version""""
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
