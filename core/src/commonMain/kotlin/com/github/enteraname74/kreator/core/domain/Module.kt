package com.github.enteraname74.kreator.core.domain

import com.github.enteraname74.kreator.core.data.Bundles
import com.github.enteraname74.kreator.core.data.Libraries
import com.github.enteraname74.kreator.core.data.Plugins
import com.github.enteraname74.kreator.core.ext.whiteSpace
import com.github.enteraname74.kreator.core.utils.BlockBuilder
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
     * Dependencies to specify in the `sourceSets` part of the build.gradle.kts file
     */
    val dependencies: List<Dependency.Implementable>,
    /**
     * Bundles names to include in the `sourceSets` part of the build.gradle.kts file
     */
    val bundlesName: List<String> = emptyList(),
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
        "$group.${name.replace(Regex("[-_s]"), "")}"

    val commonDependencies: List<Dependency.Implementable> =
        dependencies.filter {
            it.targets.containsAll(targets) && it.targets.size == targets.size
        }

    val commonBundleNames: List<String> =
        bundlesName.filter { bundle ->
            Bundles.ALL[bundle]?.firstOrNull()?.let {
                it.targets.containsAll(targets) && it.targets.size == targets.size
            } == true
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

    private fun dependenciesForTarget(target: Target): List<Dependency.Implementable> =
        dependencies.filter {
            it.targets.size == 1 && it.targets.first() == target
        }

    private fun bundlesNamesForTarget(target: Target): List<String> =
        bundlesName.filter { bundle ->
            Bundles.ALL[bundle]?.firstOrNull()?.let {
                it.targets.size == 1 && it.targets.first() == target
            } == true
        }

    private fun BlockBuilder.addRoomKaptConfiguration() {
        add(
            """
                configurations.getByName("kapt").dependencies.add(
                    DefaultExternalModuleDependency(
                        "androidx.room",
                        "room-compiler",
                        "${Libraries.ROOM.version.version}",
                    )
                )
            """
        )
    }

    private fun BlockBuilder.addComposeImplementations() {
        add("implementation(compose.runtime)")
        add("implementation(compose.foundation)")
        add("implementation(compose.material3)")
        add("implementation(compose.materialIconsExtended)")
        add("implementation(compose.components.resources)")
        add("implementation(compose.ui)")
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
                    val dependencies: List<Dependency.Implementable> = dependenciesForTarget(target)
                    val bundleNames: List<String> = bundlesNamesForTarget(target)

                    if (dependencies.isNotEmpty() || bundleNames.isNotEmpty()) {
                        target.block(this@subBlock) {
                            dependencies.forEach { dependency ->
                                add(dependency.implementation())
                            }

                            bundleNames.forEach { name ->
                                add("implementation(libs.bundles.$name)")
                            }

                            if (plugins.any { it.name == Plugins.KAPT.name }) {
                                add("")
                                addRoomKaptConfiguration()
                            }
                        }
                    }
                }
                if (commonDependencies.isNotEmpty() || commonBundleNames.isNotEmpty() || plugins.contains(Plugins.JETBRAINS_COMPOSE)) {
                    subBlock("commonMain.dependencies") {
                        if (plugins.contains(Plugins.JETBRAINS_COMPOSE)) {
                            addComposeImplementations()
                        }

                        commonDependencies.forEach { dependency ->
                            add(dependency.implementation())
                        }
                        commonBundleNames.forEach { name ->
                            add("implementation(libs.bundles.$name)")
                        }
                    }
                }
            }
        }.toString()

    private fun addAndroidLibraryBlock(androidTarget: Target.Android): String =
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

    private fun addAndroidApplicationBlock(androidTarget: Target.Android): String =
        block("android") {
            add("""namespace = "$packageName"""")
            add("compileSdk = ${androidTarget.compileSdk}")

            whiteSpace()
            subBlock("defaultConfig") {
                add("""applicationId = "$group"""")
                add("minSdk = ${androidTarget.minSdk}")
                add("targetSdk = ${androidTarget.targetSdk}")
                add("versionCode = 1")
                add("""versionName = "0.1.0"""")
                add("""testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"""")
                add("vectorDrawables.useSupportLibrary = true")
            }

            whiteSpace()
            subBlock("this.buildOutputs.all") {
                add("val variantOutputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl")
                add("""val name = "${group}_0.1.0.apk"""")
                add("variantOutputImpl.outputFileName = name")
            }

            whiteSpace()
            subBlock("buildTypes") {
                subBlock("debug") {
                    add("""manifestPlaceholders["appName"] = "${name}Debug"""")
                    add("""versionNameSuffix = "-dev"""")
                    add("""applicationIdSuffix = ".dev"""")
                }

                androidTarget.buildTypes.forEach {
                    subBlock("""create("${it.name}")""") {
                        add("""manifestPlaceholders["appName"] = "${name}Release"""")
                        add("""versionNameSuffix = "-dev.release"""")
                        add("""applicationIdSuffix = ".dev.release"""")
                        add("""signingConfig = signingConfigs.getByName("debug")""")
                        add("isMinifyEnabled = true")
                        add("proguardFiles(")
                        add("""    getDefaultProguardFile("proguard-android-optimize.txt"),""")
                        add("""    "proguard-rules.pro"""")
                        add(")")
                    }
                }

                subBlock("release") {
                    add("""manifestPlaceholders["appName"] = "$name"""")
                    add("isMinifyEnabled = true")
                    add("proguardFiles(")
                    add("""    getDefaultProguardFile("proguard-android-optimize.txt"),""")
                    add("""    "proguard-rules.pro"""")
                    add(")")
                }
            }

            subBlock("buildFeatures") {
                add("compose = true")
                add("buildConfig = true")
            }
            subBlock("composeOptions") {
                add(""" kotlinCompilerExtensionVersion = "1.5.4"""")
            }
            subBlock("packaging") {
                subBlock("resources") {
                    add("""excludes += "/META-INF/{AL2.0,LGPL2.1}"""")
                }
            }
        }.toString()

    private fun StringBuilder.addImports() {
        appendLine("import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi")

        if (plugins.any { it.name == Plugins.KAPT.name }) {
            appendLine("import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency")
        }
    }

    fun buildGradleFile(): String = buildString {
        addImports()
        whiteSpace()
        appendLine(addPlugins())
        whiteSpace()
        appendLine(addBasicInformation())
        whiteSpace()
        appendLine(addMainBlock())

        targets.find { it is Target.Android }?.let { androidTarget ->
            whiteSpace()
            if (plugins.contains(Plugins.ANDROID_APPLICATION)) {
                append(addAndroidApplicationBlock(androidTarget as Target.Android))
            } else if (plugins.contains(Plugins.ANDROID_LIBRARY)) {
                append(addAndroidLibraryBlock(androidTarget as Target.Android))
            }
        }
    }
}
