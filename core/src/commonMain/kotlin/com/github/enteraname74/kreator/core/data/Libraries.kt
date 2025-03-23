package com.github.enteraname74.kreator.core.data

import com.github.enteraname74.kreator.core.domain.BuildType
import com.github.enteraname74.kreator.core.domain.Dependency
import com.github.enteraname74.kreator.core.domain.Target

val DEFAULT_ANDROID_TARGET = Target.Android(
    compileSdk = 35,
    targetSdk = 35,
    minSdk = 26,
    buildTypes = listOf(
        BuildType(name = "dev-release"),
    ),
)

val ALL_TARGETS = listOf(
    DEFAULT_ANDROID_TARGET,
    Target.Desktop,
)

object Libraries {
    val ANDROIDX_ACTIVITY_COMPOSE = Dependency.Library(
        name = "androidx-activity-compose",
        version = Versions.ANDROIDX_ACTIVITY_COMPOSE,
        targets = listOf(DEFAULT_ANDROID_TARGET),
        module = "androidx.activity:activity-compose",
        bundleName = Bundles.ANDROIDX,
    )
    val ANDROIDX_ANNOTATION = Dependency.Library(
        name = "androidx-annotation",
        version = Versions.ANDROIDX_ANNOTATION,
        targets = listOf(DEFAULT_ANDROID_TARGET),
        module = "androidx.annotation:annotation",
        bundleName = Bundles.ANDROIDX,
    )
    val ANDROIDX_APP_COMPAT = Dependency.Library(
        name = "androidx-appcompat",
        version = Versions.ANDROIDX_APP_COMPAT,
        targets = listOf(DEFAULT_ANDROID_TARGET),
        module = "androidx.appcompat:appcompat",
        bundleName = Bundles.ANDROIDX,
    )
    val ANDROIDX_APP_COMPAT_RESOURCES = Dependency.Library(
        name = "androidx-appcompat",
        version = Versions.ANDROIDX_APP_COMPAT,
        targets = listOf(DEFAULT_ANDROID_TARGET),
        module = "androidx.appcompat:appcompat-resources",
        bundleName = Bundles.ANDROIDX,
    )
    val ANDROIDX_CORE = Dependency.Library(
        name = "androidx-core",
        version = Versions.ANDROIDX_CORE,
        targets = listOf(DEFAULT_ANDROID_TARGET),
        module = "androidx-core:core-ktx",
        bundleName = Bundles.ANDROIDX,
    )
    val ANDROIDX_LIFECYCLE = Dependency.Library(
        name = "androidx-lifecycle",
        version = Versions.ANDROIDX_LIFECYCLE,
        targets = listOf(DEFAULT_ANDROID_TARGET),
        module = "androidx.lifecycle:lifecycle-runtime-ktx",
        bundleName = Bundles.ANDROIDX,
    )

    val COIL = Dependency.Library(
        name = "coil",
        version = Versions.COIL,
        targets = ALL_TARGETS,
        module = "io.coil-kt.coil3:coil",
        bundleName = Bundles.COIL,
    )
    val COIL_COMPOSE = Dependency.Library(
        name = "coil-compose",
        version = Versions.COIL,
        targets = ALL_TARGETS,
        module = "io.coil-kt.coil3:coil-compose",
        bundleName = Bundles.COIL,
    )

    val COROUTINES_CORE = Dependency.Library(
        name = "coroutines-core",
        version = Versions.COROUTINES_CORE,
        targets = ALL_TARGETS,
        module = "org.jetbrains.kotlinx:kotlinx-coroutines-core",
    )
    val COROUTINES_CORE_SWING = Dependency.Library(
        name = "coroutines-core-swing",
        version = Versions.COROUTINES_CORE,
        targets = listOf(Target.Desktop),
        module = "org.jetbrains.kotlinx:kotlinx-coroutines-swing",
    )

    val EXPOSED_CORE = Dependency.Library(
        name = "exposed-core",
        version = Versions.EXPOSED,
        targets = listOf(Target.Desktop),
        module = "org.jetbrains.exposed:exposed-core",
        bundleName = Bundles.EXPOSED,
    )
    val EXPOSED_CRYPT = Dependency.Library(
        name = "exposed-crypt",
        version = Versions.EXPOSED,
        targets = listOf(Target.Desktop),
        module = "org.jetbrains.exposed:exposed-crypt",
        bundleName = Bundles.EXPOSED,
    )
    val EXPOSED_DAO = Dependency.Library(
        name = "exposed-dao",
        version = Versions.EXPOSED,
        targets = listOf(Target.Desktop),
        module = "org.jetbrains.exposed:exposed-dao",
        bundleName = Bundles.EXPOSED,
    )
    val EXPOSED_FLOWS = Dependency.Library(
        name = "exposed-flows",
        version = Versions.EXPOSED_FLOWS,
        targets = listOf(Target.Desktop),
        module = "com.github.enteraname74:exposed-flows",
        bundleName = Bundles.EXPOSED,
    )
    val EXPOSED_JDBC = Dependency.Library(
        name = "exposed-jdbc",
        version = Versions.EXPOSED,
        targets = listOf(Target.Desktop),
        module = "org.jetbrains.exposed:exposed-jdbc",
        bundleName = Bundles.EXPOSED,
    )
    val EXPOSED_JAVA_TIME = Dependency.Library(
        name = "exposed-java-time",
        version = Versions.EXPOSED,
        targets = listOf(Target.Desktop),
        module = "org.jetbrains.exposed:exposed-java-time",
        bundleName = Bundles.EXPOSED,
    )

    val FILE_KIT = Dependency.Library(
        name = "file-kit",
        version = Versions.FILE_KIT,
        targets = ALL_TARGETS,
        module = "io.github.vinceglb:filekit-compose",
    )

    val GSON = Dependency.Library(
        name = "gson",
        version = Versions.GSON,
        targets = listOf(Target.Desktop),
        module = "com.google.code.gson:gson",
    )

    val KOIN_ANDROIDX_COMPOSE = Dependency.Library(
        name = "koin-android-compose",
        version = Versions.KOIN,
        targets = listOf(DEFAULT_ANDROID_TARGET),
        module = "io.insert-koin:koin-androidx-compose",
    )
    val KOIN_COMPOSE = Dependency.Library(
        name = "koin-compose",
        version = Versions.KOIN,
        targets = ALL_TARGETS,
        module = "io.insert-koin:koin-compose",
    )
    val KOIN_CORE = Dependency.Library(
        name = "koin-core",
        version = Versions.KOIN,
        targets = ALL_TARGETS,
        module = "io.insert-koin:koin-core",
    )

    val KOTLIN_TEST = Dependency.Library(
        name = "kotlin-test",
        version = Versions.KOTLIN,
        targets = ALL_TARGETS,
        module = "org.jetbrains.kotlin:kotlin-test",
    )

    val KOTLINX_SERIALIZATION_CORE = Dependency.Library(
        name = "kotlinx-serialization-core",
        version = Versions.KOTLINX_SERIALIZATION,
        targets = ALL_TARGETS,
        module = "org.jetbrains.kotlinx:kotlinx-serialization-core"
    )
    val KOTLINX_SERIALIZATION_JSON = Dependency.Library(
        name = "kotlinx-serialization-json",
        version = Versions.KOTLINX_SERIALIZATION,
        targets = ALL_TARGETS,
        module = "org.jetbrains.kotlinx:kotlinx-serialization-json",
    )

    val KTOR_CLIENT_CIO = Dependency.Library(
        name = "ktor-client-cio",
        version = Versions.KTOR,
        targets = listOf(
            DEFAULT_ANDROID_TARGET,
            Target.Desktop,
        ),
        module = "io.ktor:ktor-client-cio",
    )
    val KTOR_CLIENT_CONTENT_NEGOTIATION = Dependency.Library(
        name = "ktor_client-content-negotiation",
        version = Versions.KTOR,
        targets = ALL_TARGETS,
        module = "io.ktor:ktor-client-content-negotiation",
    )
    val KTOR_CLIENT_CORE = Dependency.Library(
        name = "ktor-client-core",
        version = Versions.KTOR,
        targets = ALL_TARGETS,
        module = "io.ktor:ktor-client-core",
    )
    val KTOR_SERIALIZATION_KOTLINX_JSON = Dependency.Library(
        name = "ktor-serialization-kotlinx-json",
        version = Versions.KTOR,
        targets = listOf(DEFAULT_ANDROID_TARGET),
        module = "io.ktor:ktor-serialization-kotlinx-json",
    )
    val KTOR_SERIALIZATION_GSON = Dependency.Library(
        name = "ktor-serialization-gson",
        version = Versions.KTOR,
        targets = listOf(Target.Desktop),
        module = "io.ktor:ktor-serialization-gson",
    )

    val MULTIPLATFORM_SETTINGS = Dependency.Library(
        name = "multiplatform-settings",
        version = Versions.MULTIPLATFORM_SETTINGS,
        targets = ALL_TARGETS,
        module = "com.russhwolf:multiplatform-settings",
    )

    val VOYAGER_NAVIGATOR = Dependency.Library(
        name = "voyager-navigator",
        version = Versions.VOYAGER,
        targets = ALL_TARGETS,
        module = "cafe.adriel.voyager:voyager-navigator",
        bundleName = Bundles.VOYAGER,
    )
    val VOYAGER_SCREENMODEL = Dependency.Library(
        name = "voyager-screenmodel",
        version = Versions.VOYAGER,
        targets = ALL_TARGETS,
        module = "cafe.adriel.voyager:voyager-screenmodel",
        bundleName = Bundles.VOYAGER,
    )
    val VOYAGER_TRANSITIONS = Dependency.Library(
        name = "voyager-transitions",
        version = Versions.VOYAGER,
        targets = ALL_TARGETS,
        module = "cafe.adriel.voyager:voyager-transitions",
        bundleName = Bundles.VOYAGER,
    )
    val VOYAGER_KOIN = Dependency.Library(
        name = "voyager-koin",
        version = Versions.VOYAGER,
        targets = ALL_TARGETS,
        module = "cafe.adriel.voyager:voyager-koin",
        bundleName = Bundles.VOYAGER,
    )

    val ROOM = Dependency.Library(
        name = "room",
        version = Versions.ROOM,
        targets = listOf(DEFAULT_ANDROID_TARGET),
        module = "androidx.room:room-ktx",
    )

    val SQLITE_JDBC = Dependency.Library(
        name = "sqlite-jdbc",
        version = Versions.SQLITE_JDBC,
        targets = listOf(Target.Desktop),
        module = "org.xerial:sqlite-jdbc",
    )
}