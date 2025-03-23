package com.github.enteraname74.kreator.core.domain

import com.github.enteraname74.kreator.core.ext.whiteSpace

class VersionCatalog(
    private val plugins: List<Dependency.Plugin>,
    private val libraries: List<Dependency.Library>
) {
    val versions: List<Dependency.Version>
        get() = (plugins.map { it.version } + libraries.map { it.version }).distinct()

    fun addVersions(): String = buildString {
        appendLine("[versions]")
        versions.sortedBy { it.name }.forEach { version ->
            appendLine(version)
        }
    }

    fun addPlugins(): String = buildString {
        appendLine("[plugins]")
        plugins.sortedBy { it.name }.forEach { plugin ->
            appendLine(plugin)
        }
    }

    fun addLibraries(): String = buildString {
        appendLine("[libraries]")
        libraries.sortedBy { it.name }.forEach { library ->
            appendLine(library)
        }
    }

    fun addBundles(): String = buildString {
        appendLine("[bundles]")
        val bundles = libraries
            .sortedBy { it.bundleName }
            .groupBy { it.bundleName }
            .filterKeys { it != null }

        bundles.forEach { bundle ->
            appendLine(bundle.value.toBundle(bundleName = bundle.key!!))
        }
    }

    fun build(): String = buildString {
        append(addVersions())
        whiteSpace()
        append(addPlugins())
        whiteSpace()
        append(addLibraries())
        whiteSpace()
        append(addBundles())
    }
}