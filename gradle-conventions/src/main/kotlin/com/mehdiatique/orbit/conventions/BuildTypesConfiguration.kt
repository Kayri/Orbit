package com.mehdiatique.orbit.conventions

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

internal fun Project.configureReleaseSigningKey(
    applicationExtension: ApplicationExtension
) {
    applicationExtension.apply {
        signingConfigs {
            register("release") {
                val keystoreFile = providers.releaseKeystoreFile(project).orNull
                val storePassword = providers.releaseKeystorePassword().orNull
                val keyAlias = providers.releaseKeyAlias().orNull
                val keyPassword = providers.releaseKeyPassword().orNull

                checkNotNull(keystoreFile) { "Keystore file not provided for release signing config." }
                checkNotNull(storePassword) { "Store password not provided for release signing config." }
                checkNotNull(keyAlias) { "Key alias not provided for release signing config." }
                checkNotNull(keyPassword) { "Key password not provided for release signing config." }

                this.storeFile = keystoreFile
                this.storePassword = storePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword

                logger.lifecycle("Release signing config applied with key alias: $keyAlias")
            }
        }

        buildTypes {
            getByName("release") {
                signingConfig = signingConfigs.getByName("release")
                logger.lifecycle("Release build type configured with release signing config.")
            }
        }
    }
}

internal fun configureBuildTypes(
    applicationExtension: ApplicationExtension,
    isMinifyEnabled: Boolean = false,
    isShrinkResources: Boolean = false,
    proguardFile: String = "proguard-rules.pro"
) {
    applicationExtension.apply {
        buildTypes {
            getByName("release") {
                this.isMinifyEnabled = isMinifyEnabled
                this.isShrinkResources = isShrinkResources
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), proguardFile)
            }

            getByName("debug") {
                versionNameSuffix = ".debug"
                signingConfig = signingConfigs.getByName("debug")
            }
        }
    }
}

internal fun configureProductFlavors(
    applicationExtension: ApplicationExtension
) {
    applicationExtension.apply {
        flavorDimensions += "environment"

        productFlavors {
            create("dev") {
                dimension = "environment"
                applicationIdSuffix = ".dev"
                versionNameSuffix = "-dev"

                buildConfigField("String", "BUILD_ENV", "\"dev\"")
            }

            create("uat") {
                dimension = "environment"
                applicationIdSuffix = ".uat"
                versionNameSuffix = "-uat"

                buildConfigField("String", "BUILD_ENV", "\"uat\"")
            }

            create("prod") {
                dimension = "environment"
                buildConfigField("String", "BUILD_ENV", "\"prod\"")
            }
        }
    }
}