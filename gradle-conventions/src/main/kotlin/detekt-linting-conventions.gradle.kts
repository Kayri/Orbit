import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("io.gitlab.arturbosch.detekt")
}

val libs = the<LibrariesForLibs>()


detekt {
    buildUponDefaultConfig = true
    config.setFrom("$rootDir/detekt.yml")
}

// Kotlin DSL
tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

tasks.named("check") {
    dependsOn("detekt")  // Ensure Detekt runs before 'check'
}

dependencies {
    detektPlugins(libs.arturbosch.detekt.formatting)
}