package com.github.enteraname74.kreator.core

import com.github.enteraname74.kreator.core.data.ALL_TARGETS
import com.github.enteraname74.kreator.core.data.Libraries
import com.github.enteraname74.kreator.core.data.Plugins
import com.github.enteraname74.kreator.core.domain.Module
import com.github.enteraname74.kreator.core.domain.Project

fun main() {
    val project = Project(
        name = "KREATOR_TEST_PROJECT",
        path = "/home/noah/Documents/Projets",
        modules = listOf(
            Module(
                name = "domain",
                group = "com.github.enteraname74.kreatortestproject",
                description = "domain part",
                javaVersion = "17",
                dependencies = listOf(
                    Libraries.COROUTINES_CORE,
                ),
                plugins = listOf(
                    Plugins.KOTLIN_MULTIPLATFORM,
                    Plugins.ANDROID_LIBRARY,
                ),
                targets = ALL_TARGETS,
            )
        )
    )

    ProjectGenerator.generateProject(project)
}