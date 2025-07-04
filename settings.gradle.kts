pluginManagement {
    includeBuild("gradle-conventions")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Orbit"
include(":app")
include(":core:data")
include(":core:database")
include(":core:navigation-contract")
include(":core:ui-contract")
include(":design-system")
include(":feature:contacts")
include(":feature:insight")
include(":feature:action")
