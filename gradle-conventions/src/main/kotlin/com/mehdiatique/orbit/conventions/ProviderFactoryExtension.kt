package com.mehdiatique.orbit.conventions

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import java.io.File

const val NO_VALUE_SET = "NO_VALUE_SET"

/**
 * Obtains the release keystore file for signing from CI environment or defaults to a placeholder.
 */
fun ProviderFactory.releaseKeystoreFile(project: Project): Provider<File> =
    systemProperty("keystoreFile")
        .orElse(NO_VALUE_SET)
        .map { project.file(it) }

/**
 * Obtains the keystore password for signing from CI environment or defaults to a placeholder.
 */
fun ProviderFactory.releaseKeystorePassword(): Provider<String> =
    systemProperty("keystorePassword")
        .orElse(NO_VALUE_SET)

/**
 * Obtains the key alias for signing from CI environment or defaults to a placeholder.
 */
fun ProviderFactory.releaseKeyAlias(): Provider<String> =
    systemProperty("keyAlias")
        .orElse(NO_VALUE_SET)

/**
 * Obtains the key password for signing from CI environment or defaults to a placeholder.
 */
fun ProviderFactory.releaseKeyPassword(): Provider<String> =
    systemProperty("keyPassword")
        .orElse(NO_VALUE_SET)
