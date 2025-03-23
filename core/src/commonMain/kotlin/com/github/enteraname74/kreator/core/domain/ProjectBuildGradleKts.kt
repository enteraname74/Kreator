package com.github.enteraname74.kreator.core.domain

import com.github.enteraname74.kreator.core.utils.block

class ProjectBuildGradleKts(
    private val aliasPluginsPath: List<String>
) {
    private fun addPlugins(): String =
        block("plugins") {
            aliasPluginsPath.forEach { plugin ->
                add("alias($plugin) apply false")
            }
        }.toString()

    fun build(): String = buildString {
        appendLine(addPlugins())
    }
}