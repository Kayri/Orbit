import com.android.build.api.dsl.LibraryExtension
import com.mehdiatique.orbit.conventions.configureKotlinToolchain
import com.mehdiatique.orbit.conventions.configureBuildConfig
import com.mehdiatique.orbit.conventions.configureCoreLibraryDesugaring
import com.mehdiatique.orbit.conventions.configurePackagingOptions
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("detekt-linting-conventions")
}

val libs = the<LibrariesForLibs>()

// Configure the Kotlin toolchain
configureKotlinToolchain()

// Configure the LibraryExtension with all necessary configurations in one block
extensions.configure<LibraryExtension> {
    compileSdk = libs.versions.androidSdkCompile.get().toInt()
    defaultConfig.minSdk = libs.versions.androidSdkMin.get().toInt()

    configureBuildConfig(this)
    configureCoreLibraryDesugaring(this)
    configurePackagingOptions(this)
}
