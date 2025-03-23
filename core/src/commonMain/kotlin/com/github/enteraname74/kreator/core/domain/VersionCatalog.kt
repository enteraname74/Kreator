package com.github.enteraname74.kreator.core.domain

import com.github.enteraname74.kreator.core.data.Bundles
import com.github.enteraname74.kreator.core.ext.whiteSpace

class VersionCatalog(
    private val plugins: List<Dependency.Plugin>,
    private val libraries: List<Dependency.Library>,
    private val bundlesNames: List<String>,
) {
    val versions: List<Dependency.Version>
        get() = (plugins.mapNotNull { it.version } + libraries.map { it.version }).distinct()

    fun addVersions(): String = buildString {
        appendLine("[versions]")

        val withBundles = versions + Bundles.ALL
            .filterKeys { bundlesNames.contains(it) }
            .values
            .flatten()
            .map { it.version }
            .distinctBy { it.name }

        withBundles.sortedBy { it.name }.forEach { version ->
            appendLine(version)
        }
    }

    fun addPlugins(): String = buildString {
        appendLine("[plugins]")
        plugins.filter { it.mode == Dependency.Plugin.Mode.Alias }.sortedBy { it.name }.forEach { plugin ->
            appendLine(plugin.tomlDeclaration())
        }
    }

    fun addLibraries(): String = buildString {
        appendLine("[libraries]")

        val withBundles = libraries + Bundles.ALL
            .filterKeys { bundlesNames.contains(it) }
            .values
            .flatten()
            .distinctBy { it.name }

        withBundles.sortedBy { it.name }.forEach { library ->
            appendLine(library)
        }
    }

    fun addBundles(): String = buildString {
        if (bundlesNames.isNotEmpty()) {
            appendLine("[bundles]")
            val bundles = Bundles.ALL.filterKeys { bundlesNames.contains(it) }

            bundles.forEach { bundle ->
                appendLine(bundle.value.toBundle(bundleName = bundle.key))
            }
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