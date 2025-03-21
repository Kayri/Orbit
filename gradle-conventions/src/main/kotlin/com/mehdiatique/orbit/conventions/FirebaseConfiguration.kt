package com.mehdiatique.orbit.conventions

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

/**
 * Imports the Firebase BOM to ensure consistent versioning for Firebase dependencies.
 *
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.addFirebaseBom(libs: LibrariesForLibs = the()) {
    dependencies {
        add("implementation", platform(libs.google.firebase.bom))
    }
}

/**
 * Adds Firebase Analytics dependency.
 *
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.addFirebaseAnalytics(libs: LibrariesForLibs = the()) {
    dependencies {
        add("implementation", libs.google.firebase.analytics)
    }
}

/**
 * Adds Firebase Crashlytics dependency.
 *
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.addFirebaseCrashlytics(libs: LibrariesForLibs = the()) {
    dependencies {
        add("implementation", libs.google.firebase.crashlytics)
    }
}
