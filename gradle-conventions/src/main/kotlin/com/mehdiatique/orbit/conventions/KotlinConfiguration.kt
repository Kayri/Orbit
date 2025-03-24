package com.mehdiatique.orbit.conventions

import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

/**
 * Configures the Kotlin JVM toolchain to use the specified Java language version.
 * Defaults to Java 17 if no version is provided.
 *
 * @param javaVersion The Java version to be used for the Kotlin JVM toolchain (defaults to 17).
 */
internal fun Project.configureKotlinToolchain(
    javaVersion: JavaLanguageVersion = JavaLanguageVersion.of(19)
) {
    // Ensure the javaVersion is supported
    require(javaVersion.asInt() >= 8) { "Java version must be 8 or higher." }

    // Configure Kotlin JVM toolchain with the specified Java version
    extensions.configure<KotlinProjectExtension> {
        jvmToolchain {
            languageVersion.set(javaVersion)
        }
    }

    // Optionally log or print the configured version
    logger.lifecycle("Kotlin JVM toolchain configured with Java ${javaVersion.asInt()}.")
}