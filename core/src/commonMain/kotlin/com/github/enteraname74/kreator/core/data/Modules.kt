package com.github.enteraname74.kreator.core.data

import com.github.enteraname74.kreator.core.domain.Dependency
import com.github.enteraname74.kreator.core.domain.Module
import com.github.enteraname74.kreator.core.domain.Project
import com.github.enteraname74.kreator.core.domain.Target
import com.github.enteraname74.kreator.core.domain.hasAndroidTarget
import com.github.enteraname74.kreator.core.domain.hasDesktopTarget

object Modules {
    private fun MutableList<Dependency.Plugin>.default(targets: List<Target>) {
        add(Plugins.KOTLIN_MULTIPLATFORM)

        if (targets.hasAndroidTarget()) {
            add(Plugins.ANDROID_LIBRARY)
        }
    }

    fun domain(
        project: Project
    ): Module =
        Module(
            name = "domain",
            group = project.group,
            description = "Domain part of the project",
            javaVersion = project.javaVersion,
            targets = project.targets,
            plugins = buildList {
                default(project.targets)
            },
            dependencies = listOf(
                Libraries.COROUTINES_CORE
            )
        )

    fun repository(
        project: Project
    ): Module =
        Module(
            name = "repository",
            group = project.group,
            description = "Repository part of the project for managing data",
            javaVersion = project.javaVersion,
            targets = project.targets,
            plugins = buildList {
                default(project.targets)
            },
            dependencies = listOf(
                Libraries.COROUTINES_CORE,
                Dependency.Module(
                    name = "domain",
                    targets = ALL_TARGETS,
                )
            ),
        )

    fun local(
        project: Project
    ): Module =
        Module(
            name = "local",
            group = project.group,
            description = "Local part of the project for local database",
            javaVersion = project.javaVersion,
            targets = project.targets,
            plugins = buildList {
                default(project.targets)
                add(Plugins.KAPT)
            },
            dependencies = buildList {
                add(Libraries.COROUTINES_CORE)

                if (project.targets.hasAndroidTarget()) {
                    add(Libraries.ROOM)
                }

                if (project.targets.hasDesktopTarget()) {
                    add(Libraries.SQLITE_JDBC)
                }

                add(
                    Dependency.Module(
                        name = "domain",
                        targets = ALL_TARGETS,
                    )
                )
                add(
                    Dependency.Module(
                        name = "repository",
                        targets = ALL_TARGETS,
                    )
                )
            },
            bundlesName = buildList {
                if (project.targets.hasDesktopTarget()) {
                    add(Bundles.EXPOSED)
                }
            }
        )

    fun remote(
        project: Project
    ): Module =
        Module(
            name = "remote",
            group = project.group,
            description = "Remote part of the project",
            javaVersion = project.javaVersion,
            targets = project.targets,
            plugins = buildList {
                default(project.targets)
                add(Plugins.KOTLIN_SERIALIZATION)
            },
            dependencies = buildList {
                add(Libraries.COROUTINES_CORE)
                add(Libraries.KOTLINX_SERIALIZATION_JSON)
                add(Libraries.KTOR_CLIENT_CIO)
                add(Libraries.KTOR_CLIENT_CORE)
                add(Libraries.KTOR_CLIENT_CONTENT_NEGOTIATION)

                if (project.targets.hasAndroidTarget()) {
                    add(Libraries.KTOR_SERIALIZATION_KOTLINX_JSON)
                }
                if (project.targets.hasDesktopTarget()) {
                    add(Libraries.KTOR_SERIALIZATION_GSON)
                }

                add(
                    Dependency.Module(
                        name = "domain",
                        targets = ALL_TARGETS,
                    )
                )
                add(
                    Dependency.Module(
                        name = "repository",
                        targets = ALL_TARGETS,
                    )
                )
            },
        )

    fun config(
        project: Project
    ): Module =
        Module(
            name = "config",
            group = project.group,
            description = "Config part of the project (DI,...)",
            javaVersion = project.javaVersion,
            targets = project.targets,
            plugins = buildList {
                default(project.targets)
            },
            dependencies = listOf(
                Libraries.COROUTINES_CORE,
                Libraries.KOIN_CORE,
                Dependency.Module(
                    name = "domain",
                    targets = ALL_TARGETS,
                ),
                Dependency.Module(
                    name = "repository",
                    targets = ALL_TARGETS,
                ),
                Dependency.Module(
                    name = "local",
                    targets = ALL_TARGETS,
                ),
                Dependency.Module(
                    name = "remote",
                    targets = ALL_TARGETS,
                )
            )
        )

    fun coreUi(
        project: Project
    ): Module =
        Module(
            name = "core-ui",
            group = project.group,
            description = "Core UI part of the project for reusable UI elements",
            javaVersion = project.javaVersion,
            targets = project.targets,
            plugins = buildList {
                default(project.targets)
                add(Plugins.COMPOSE_COMPILER)
                add(Plugins.JETBRAINS_COMPOSE)
            },
            dependencies = emptyList(),
            bundlesName = buildList {
                if (project.targets.hasAndroidTarget()) {
                    add(Bundles.ANDROIDX)
                }
            }
        )

    fun app(
        project: Project
    ): Module =
        Module(
            name = "app",
            group = project.group,
            description = "App part of the project",
            javaVersion = project.javaVersion,
            targets = project.targets,
            plugins = buildList {
                if (project.targets.hasAndroidTarget()) {
                    add(Plugins.ANDROID_APPLICATION)
                }
                add(Plugins.KOTLIN_MULTIPLATFORM)
                add(Plugins.COMPOSE_COMPILER)
                add(Plugins.JETBRAINS_COMPOSE)
            },
            dependencies = buildList {
                if (project.targets.hasAndroidTarget()) {
                    add(Libraries.KOIN_ANDROIDX_COMPOSE)
                }
                add(Libraries.KOIN_COMPOSE)
                add(Libraries.KOIN_CORE)

                add(
                    Dependency.Module(
                        name = "domain",
                        targets = ALL_TARGETS,
                    )
                )
                add(
                    Dependency.Module(
                        name = "core-ui",
                        targets = ALL_TARGETS,
                    )
                )
                add(
                    Dependency.Module(
                        name = "config",
                        targets = ALL_TARGETS,
                    )
                )
            },
            bundlesName = buildList {
                if (project.targets.hasAndroidTarget()) {
                    add(Bundles.ANDROIDX)
                }
            }
        )
}