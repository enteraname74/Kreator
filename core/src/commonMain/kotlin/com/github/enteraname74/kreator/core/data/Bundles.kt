package com.github.enteraname74.kreator.core.data

import com.github.enteraname74.kreator.core.domain.Dependency

object Bundles {
    const val ANDROIDX = "androidx"
    const val COIL = "coil"
    const val EXPOSED = "exposed"
    const val VOYAGER = "voyager"

    val ALL: Map<String, List<Dependency.Library>> = Libraries.ALL
        .groupBy { it.bundleName }
        .filterKeys { it != null }
        .mapKeys { it.key!! }
}