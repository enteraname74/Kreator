package com.github.enteraname74.kreator.core.domain

import com.github.enteraname74.kreator.core.ext.whiteSpace
import com.github.enteraname74.kreator.core.utils.block

class SettingsGradleKts(
    private val projectName: String,
    private val modulesNames: List<String>,
) {
    private fun addPluginManagement(): String =
        block("pluginManagement") {
            subBlock("repositories") {
                add("google()")
                add("mavenCentral()")
                add("gradlePluginPortal()")
            }
        }.toString()

    private fun addPlugins(): String =
        block("plugins") {
            add("""id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"""")
        }.toString()

    private fun addDependencyResolutionManagement(): String =
        block("dependencyResolutionManagement") {
            subBlock("repositories") {
                add("google()")
                add("mavenCentral()")
                subBlock("maven") {
                    add("""url = uri("https://jitpack.io")""")
                }
            }
        }.toString()

    private fun StringBuilder.addModules() {
        modulesNames.sorted().forEach { module ->
            appendLine("""include(":$module")""")
        }
    }

    fun build(): String = buildString {
        appendLine(addPluginManagement())
        whiteSpace()

        appendLine(addPlugins())
        whiteSpace()

        appendLine(addDependencyResolutionManagement())
        whiteSpace()

        appendLine("""rootProject.name = "$projectName"""")
        whiteSpace()

        addModules()
    }
}