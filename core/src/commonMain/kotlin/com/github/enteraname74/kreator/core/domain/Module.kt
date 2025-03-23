package com.github.enteraname74.kreator.core.domain

import com.github.enteraname74.kreator.core.ext.whiteSpace
import com.github.enteraname74.kreator.core.utils.block

class Module(
    /**
     * Name of the module (folder)
     */
    val name: String,
    /**
     * `group` field in build.gradle.kts
     */
    val group: String,
    /**
     * `version` field in build.gradle.kts
     */
    val version: String? = null,
    /**
     * `description` field in build.gradle.kts
     */
    val description: String,
    /**
     * Java version used in the module
     */
    val javaVersion: String,
    /**
     * Dependencies to specify in `sourceSets` part of the build.gradle.kts file
     */
    val dependencies: List<Dependency.Library>,
    /**
     * Plugins to add at the top of the build.gradle.kts file
     */
    val plugins: List<Dependency.Plugin>,
    /**
     * Multiplatform targets of the module
     */
    val targets: List<Target>,
) {
    val packageName: String =
        "$group.$name"

    val commonDependencies: List<Dependency.Library> =
        dependencies.filter {
            it.targets.containsAll(targets) && it.targets.size == targets.size
        }

    private fun addPlugins(): String =
        block("plugins") {
            plugins.forEach { plugin ->
                add(plugin.usePlugin())
            }
        }.toString()

    private fun addBasicInformation(): String = buildString {
        appendLine("""group = "$group"""")
        appendLine("""description = "$description"""")
        version?.let { append("""version = "$version"""") }
    }

    private fun dependenciesForTarget(target: Target): List<Dependency.Library> =
        dependencies.filter {
            it.targets.size == 1 && it.targets.first() == target
        }

    private fun addMainBlock(): String =
        block("kotlin") {
            add("jvmToolchain($javaVersion)")
            targets.forEach { target ->
                add(target)
            }

            whiteSpace()
            add("@OptIn(ExperimentalKotlinGradlePluginApi::class)")
            subBlock("compilerOptions") {
                add("// Common compiler options applied to all Kotlin source sets for expect / actual implementations")
                add("freeCompilerArgs.add(\"-Xexpect-actual-classes\")")
            }
            whiteSpace()

            subBlock("sourceSets") {
                targets.forEach { target ->
                    val dependencies: List<Dependency.Library> = dependenciesForTarget(target)

                    if (dependencies.isNotEmpty()) {
                        target.block(this@subBlock) {
                            dependencies.forEach { dependency ->
                                add(dependency.implementation())
                            }
                        }
                    }
                }
                subBlock("commonMain.dependencies") {
                    commonDependencies.forEach { dependency ->
                        add(dependency.implementation())
                    }
                }
            }
        }.toString()

    private fun addAndroidBlock(androidTarget: Target.Android): String =
        block("android") {
            add("""namespace = "$packageName"""")
            add("compileSdk = ${androidTarget.compileSdk}")

            whiteSpace()
            subBlock("defaultConfig") {
                add("minSdk = ${androidTarget.minSdk}")
            }

            whiteSpace()
            subBlock("buildTypes") {
                androidTarget.buildTypes.forEach {
                    add("""create("${it.name}")""")
                }
            }
        }.toString()

    fun buildGradleFile(): String = buildString {
        appendLine("import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi")
        whiteSpace()
        appendLine(addPlugins())
        whiteSpace()
        appendLine(addBasicInformation())
        whiteSpace()
        appendLine(addMainBlock())

        targets.find { it is Target.Android }?.let { androidTarget ->
            whiteSpace()
            append(addAndroidBlock(androidTarget as Target.Android))
        }
    }
}
