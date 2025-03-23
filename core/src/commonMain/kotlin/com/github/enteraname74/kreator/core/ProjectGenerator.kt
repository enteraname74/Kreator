package com.github.enteraname74.kreator.core

import com.github.enteraname74.kreator.core.domain.Dependency
import com.github.enteraname74.kreator.core.domain.Module
import com.github.enteraname74.kreator.core.domain.Project
import com.github.enteraname74.kreator.core.domain.ProjectBuildGradleKts
import com.github.enteraname74.kreator.core.domain.SettingsGradleKts
import com.github.enteraname74.kreator.core.domain.VersionCatalog
import java.io.File

object ProjectGenerator {

    private fun getResourcesFile(fileName: String): File =
        File("src/commonMain/resources/$fileName")

    private fun writeProjectGradleFiles(projectDirectory: File, project: Project) {
        val projectBuildGradleKts = ProjectBuildGradleKts(
            aliasPluginsPath = buildList {
                project.modules.forEach { module ->
                    addAll(
                        module
                            .plugins
                            .filter { it.mode == Dependency.Plugin.Mode.Alias }
                            .map { it.tomlPath }
                    )
                }
            }.distinct().sorted()
        )
        File(projectDirectory, "build.gradle.kts").writeText(projectBuildGradleKts.build())

        val settingsGradleKts = SettingsGradleKts(
            projectName = project.name,
            modulesNames = project.modules.map { it.name },
        )
        File(projectDirectory, "settings.gradle.kts").writeText(settingsGradleKts.build())
    }

    private fun writeGradlewFiles(projectDirectory: File) {
        val gradlew = File(projectDirectory, "gradlew")

        getResourcesFile("gradlew").copyTo(gradlew, overwrite = true)

        val gradleProperties = File(projectDirectory, "gradle.properties")
        getResourcesFile("gradle.properties").copyTo(gradleProperties, overwrite = true)

        val gradlewBat = File(projectDirectory, "gradlew.bat")
        getResourcesFile("gradlew.bat").copyTo(gradlewBat, overwrite = true)
    }

    private fun writeProjectGitignore(projectDirectory: File) {
        val gitignore = File(projectDirectory, ".gitignore")
        getResourcesFile(".gitignore").copyTo(gitignore, overwrite = true)
    }

    @Suppress("UNCHECKED_CAST")
    private fun writeGradleFolder(projectDirectory: File, project: Project) {
        val gradleFolder = File(projectDirectory, "gradle").also { it.mkdirs() }

        val versionCatalog = VersionCatalog(
            plugins = project.modules.map { it.plugins }.flatten().distinct(),
            libraries = project.modules.map { it.dependencies }
                .flatten()
                .filter { it is Dependency.Library }
                .distinct() as List<Dependency.Library>,
            bundlesNames = project.modules
                .map { it.bundlesName }
                .flatten()
                .distinct()
        )

        File(gradleFolder, "libs.versions.toml").writeText(versionCatalog.build())

        val wrapperFolder = File(gradleFolder, "wrapper").also { it.mkdir() }
        val wrapper = File(wrapperFolder, "gradle-wrapper.jar")
        getResourcesFile("gradle-wrapper.jar").copyTo(wrapper, overwrite = true)

        val wrapperProperties = File(wrapperFolder, "gradle-wrapper.properties")
        getResourcesFile("gradle-wrapper.properties").copyTo(wrapperProperties, overwrite = true)
    }

    private fun writeModule(projectDirectory: File, module: Module) {
        val moduleFolder = File(projectDirectory, module.name).also { it.mkdirs() }
        File(moduleFolder, "build.gradle.kts").writeText(module.buildGradleFile())
        File(moduleFolder, "src/commonMain/kotlin/${module.packageName.replace(".", "/")}").mkdirs()
        File(moduleFolder, ".gitignore").writeText("/build")
    }

    fun generateProject(project: Project) {
        val projectDirectory = File(project.completePath).also { it.mkdirs() }

        writeProjectGradleFiles(projectDirectory, project)
        writeGradlewFiles(projectDirectory)
        writeProjectGitignore(projectDirectory)
        writeGradleFolder(projectDirectory, project)

        project.modules.forEach { module ->
            writeModule(projectDirectory, module)
        }
    }
}