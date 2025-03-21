import com.mehdiatique.orbit.conventions.configureAndroidTests
import com.mehdiatique.orbit.conventions.configureBuildConfig
import com.mehdiatique.orbit.conventions.configureBuildTypes
import com.mehdiatique.orbit.conventions.configureCoreLibraryDesugaring
import com.mehdiatique.orbit.conventions.configureKotlinToolchain
import com.mehdiatique.orbit.conventions.configurePackagingOptions
import com.mehdiatique.orbit.conventions.configureProductFlavors
import com.mehdiatique.orbit.conventions.configureReleaseSigningKey
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("detekt-linting-conventions")
}

val libs = the<LibrariesForLibs>()

configureKotlinToolchain()

extensions.configure<ApplicationExtension> {
    compileSdk = libs.versions.androidSdkCompile.get().toInt()
    defaultConfig.minSdk = libs.versions.androidSdkMin.get().toInt()
    defaultConfig.targetSdk = libs.versions.androidSdkTarget.get().toInt()

    configureBuildConfig(this)
    configureCoreLibraryDesugaring(this)
    configureReleaseSigningKey(this)
    configureBuildTypes(this)
    configureProductFlavors(this)
    configurePackagingOptions(this)
    configureAndroidTests(this)
}