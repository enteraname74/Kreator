package com.github.enteraname74.kreator.core.domain

import com.github.enteraname74.kreator.core.utils.block

class ProjectBuildGradleKts(
    private val hasAndroidPlugins: Boolean,
    private val aliasPluginsPath: List<String>
) {
    private fun addBuildScript(): String =
        block("buildscript") {
            subBlock("repositories") {
                add("mavenCentral()")
                add("google()")
            }
            subBlock("dependencies") {
                add("classpath(libs.gradle)")
            }
        }.toString()

    private fun addPlugins(): String =
        block("plugins") {
            aliasPluginsPath.forEach { plugin ->
                add("alias($plugin) apply false")
            }
        }.toString()

    fun build(): String = buildString {
        if (hasAndroidPlugins) {
            appendLine(addBuildScript())
        }
        appendLine(addPlugins())
    }
}