package com.mehdiatique.orbit.conventions

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

/**
 * Adds the Jetpack Compose BOM to ensure consistent versioning for Compose dependencies.
 *
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.addComposeBom(libs: LibrariesForLibs = the()) {
    dependencies {
        add("implementation", platform(libs.androidx.compose.bom))
        add("androidTestImplementation", platform(libs.androidx.compose.bom))
    }
}

/**
 * Adds Compose UI dependencies and optionally Compose Tooling and Material libraries.
 *
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.addComposeUiDependencies(libs: LibrariesForLibs = the()) {
    dependencies {
        add("implementation", libs.androidx.ui)
        add("debugImplementation", libs.androidx.ui.tooling)
        add("implementation", libs.androidx.ui.tooling.preview)
    }
}

/**
 * Adds MockK dependencies for Compose previews and testing.
 *
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.addMockkDependencies(libs: LibrariesForLibs = the()) {
    dependencies {
        add("implementation", libs.mockk)
        add("androidTestImplementation", libs.mockk.android)
    }
}