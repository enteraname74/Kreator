package com.github.enteraname74.kreator.core.domain

class Project(
    val name: String,
    val group: String,
    val path: String,
    val javaVersion: String,
    var modules: List<Module> = emptyList(),
    val targets: List<Target>,
) {
    val completePath: String
        get() = "$path/$name"
}