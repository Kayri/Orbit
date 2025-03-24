import com.mehdiatique.orbit.conventions.addComposeBom
import com.mehdiatique.orbit.conventions.addComposeUiDependencies
import com.mehdiatique.orbit.conventions.addMockkDependencies
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
}

pluginManager.withPlugin("com.android.application") {
    extensions.configure<ApplicationExtension> {
        buildFeatures { compose = true }
    }
}

pluginManager.withPlugin("com.android.library") {
    extensions.configure<LibraryExtension> {
        buildFeatures { compose = true }
    }
}

composeCompiler {

}

addComposeBom()
addComposeUiDependencies()
addMockkDependencies()
