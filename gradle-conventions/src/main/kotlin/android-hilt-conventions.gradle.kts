import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

val libs = the<LibrariesForLibs>()
dependencies {
    add("implementation", libs.google.dagger.hilt.android)
    add("ksp", libs.google.dagger.hilt.compiler)
    add("implementation", libs.androidx.hilt.navigation.compose)
}
