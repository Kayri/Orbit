package com.mehdiatique.orbit.conventions

import com.android.build.api.dsl.ManagedVirtualDevice
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the

/**
 * Configures the Android instrumentation runner.
 * Sets the instrumentation runner to AndroidJUnitRunner and adds the required dependencies.
 *
 * @param commonExtension The Android build extension to configure.
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.configureInstrumentationRunner(
    commonExtension: AnyCommonExtension,
    libs: LibrariesForLibs = the()
) {
    commonExtension.apply {
        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    dependencies {
        add("implementation", libs.androidx.test.runner)
    }
}

/**
 * Configures MockK for Android tests by enabling legacy JNI packaging.
 *
 * @param commonExtension The Android build extension to configure.
 */
@Suppress("UnstableApiUsage")
internal fun configureMockkAndroidTests(
    commonExtension: AnyCommonExtension
) {
    commonExtension.apply {
        testOptions {
            packaging {
                jniLibs {
                    useLegacyPackaging = true
                }
            }
        }
    }
}

/**
 * Configures managed test devices for testing.
 *
 * @param commonExtension The Android build extension to configure.
 * @param deviceName The name of the device (default is "Pixel 5").
 * @param apiLevel The API level for the device (default is 30).
 * @param systemImageSource The system image source (default is "google").
 */
@Suppress("UnstableApiUsage", "MagicNumber")
internal fun configureTestDevices(
    commonExtension: AnyCommonExtension,
    deviceName: String = "Pixel 5",
    apiLevel: Int = 30,
    systemImageSource: String = "google"
) = commonExtension.apply {
    testOptions {
        managedDevices {
            devices.register<ManagedVirtualDevice>("${deviceName.lowercase().replace(" ", "")}api$apiLevel") {
                this.device = deviceName
                this.apiLevel = apiLevel
                this.systemImageSource = systemImageSource
            }
        }
    }
}

/**
 * Enables Android resources in unit tests.
 *
 * @param commonExtension The Android build extension to configure.
 */
@Suppress("UnstableApiUsage")
internal fun configureUnitTestAndroidResources(
    commonExtension: AnyCommonExtension
) = commonExtension.apply {
    testOptions {
        unitTests { isIncludeAndroidResources = true }
    }
}

/**
 * Adds Robolectric dependencies for unit tests.
 *
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.addRobolectricDependencies(libs: LibrariesForLibs = the()) {
    dependencies {
        add("testImplementation", libs.robolectric)
    }
}

/**
 * Adds AndroidX test dependencies including core and JUnit extensions.
 *
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.addAndroidXTestDependencies(libs: LibrariesForLibs = the()) {
    dependencies {
        add("testImplementation", libs.androidx.test.core)
        add("testImplementation", libs.androidx.junit)
        add("testImplementation", libs.androidx.test.runner)
    }
}

/**
 * Adds Jetpack Compose test dependencies for UI testing.
 *
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.addComposeTestDependencies(libs: LibrariesForLibs = the()) {
    dependencies {
        add("testImplementation", libs.androidx.ui.test.junit4)
        add("implementation", libs.androidx.ui.test.manifest)
    }
}

/**
 * A single function that configures all the necessary Android test configurations.
 * It includes:
 * - Instrumentation runner setup.
 * - MockK JNI configuration.
 * - Managed test devices setup.
 * - Android resources for unit tests.
 * - Adding test dependencies like Robolectric, AndroidX test libraries, and Jetpack Compose testing tools.
 *
 * @param commonExtension The Android build extension to configure.
 * @param libs The version catalog used to retrieve dependency versions.
 */
internal fun Project.configureAndroidTests(
    commonExtension: AnyCommonExtension,
    libs: LibrariesForLibs = the(),
    enableRobolectric: Boolean = true,
    enableComposeTests: Boolean = true
) {
    // Configure Instrumentation Runner
    configureInstrumentationRunner(commonExtension, libs)

    // Configure MockK for Android Tests
    configureMockkAndroidTests(commonExtension)

    // Configure Test Devices
    configureTestDevices(commonExtension)

    // Enable Android Resources for Unit Tests
    configureUnitTestAndroidResources(commonExtension)

    // Add AndroidX Test Dependencies
    addAndroidXTestDependencies(libs)

    // Add optional dependencies based on flags
    if (enableRobolectric) {
        addRobolectricDependencies(libs)
    }

    if (enableComposeTests) {
        addComposeTestDependencies(libs)
    }
}