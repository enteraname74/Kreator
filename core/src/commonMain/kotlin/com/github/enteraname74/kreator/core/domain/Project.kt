package com.github.enteraname74.kreator.core.domain

class Project(
    val name: String,
    val path: String,
    val modules: List<Module>,
) {
    val completePath: String
        get() = "$path/$name"
}