package com.mehdiatique.orbit.conventions

import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

typealias AnyCommonExtension = CommonExtension<*, *, *, *, *, *>

internal fun configureBuildConfig(
    commonExtension: AnyCommonExtension,
    enableBuildConfig: Boolean = true
) = commonExtension.apply {
    buildFeatures {
        buildConfig = enableBuildConfig
    }
}

internal fun Project.configureCoreLibraryDesugaring(
    commonExtension: AnyCommonExtension,
    libs: LibrariesForLibs = the()
) {
    commonExtension.apply {
        compileOptions { isCoreLibraryDesugaringEnabled = true }
    }

    dependencies {
        add("coreLibraryDesugaring", libs.android.desugaring)
    }
}

internal fun configurePackagingOptions(
    commonExtension: AnyCommonExtension,
    additionalExcludes: List<String> = emptyList()
) = commonExtension.apply {
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/LICENSE*.md"
            excludes += additionalExcludes
        }
    }
}