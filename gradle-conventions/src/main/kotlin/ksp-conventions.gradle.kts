/**
 * Applies the Kotlin Symbol Processing (KSP) plugin to the target module.
 *
 * This convention is created to provide fine-grained control over KSP usage
 * across modules. Instead of applying KSP globally via a shared convention
 * (like `android-library-conventions`), this plugin is applied only where
 * annotation processing is actually required (e.g., Room, Hilt, etc.).
 *
 * Benefits of this approach:
 * - Avoids unnecessary application of the KSP plugin in modules that don’t use it.
 * - Makes plugin usage more explicit and intentional.
 * - Improves build clarity and maintainability.
 *
 * Usage:
 * Apply this plugin in the `build.gradle.kts` file of any module that requires KSP:
 * ```
 * plugins {
 *     id("android-library-conventions")
 *     id("ksp-conventions")
 * }
 * ```
 */
plugins {
    id("com.google.devtools.ksp")
}

