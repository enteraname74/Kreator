package com.github.enteraname74.kreator.core

import com.github.enteraname74.kreator.core.data.ALL_TARGETS
import com.github.enteraname74.kreator.core.data.Modules
import com.github.enteraname74.kreator.core.domain.Project

fun main() {
    val project = Project(
        name = "KREATOR_TEST_PROJECT",
        path = "/home/noah/Documents/Projets",
        javaVersion = "17",
        group = "com.github.enteraname74.kreatortestproject",
        targets = ALL_TARGETS,
    ).apply {
        modules = listOf(
            Modules.domain(this),
            Modules.app(this),
            Modules.remote(this),
            Modules.repository(this),
            Modules.local(this),
            Modules.config(this),
            Modules.coreUi(this)
        )
    }

    ProjectGenerator.generateProject(project)
}