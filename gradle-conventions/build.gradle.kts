plugins {
    `kotlin-dsl`
}

dependencies {
    // Share Version Catalog with plugins
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.android.gradlePlugin)
    implementation(libs.arturbosch.detekt.gradle.plugin)
    implementation(libs.google.dagger.hilt.plugin)
    implementation(libs.google.devtools.ksp)
    implementation(libs.google.services)
    implementation(libs.jetbrains.compose.compiler)
    implementation(libs.jetbrains.kotlin.gradlePlugin)
}