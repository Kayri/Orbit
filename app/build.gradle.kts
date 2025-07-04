plugins {
    id("android-application-conventions")
    id("android-compose-conventions")
    id("android-hilt-conventions")
}

android {
    namespace = "com.mehdiatique.orbit"

    defaultConfig {
        applicationId = "com.mehdiatique.orbit"
        versionCode = 1
        versionName = "1.0.0"
    }
}
dependencies {
    implementation(project(":core:ui-contract"))
    implementation(project(":design-system"))
    implementation(project(":feature:contacts"))
    implementation(project(":feature:insight"))
    implementation(project(":feature:action"))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
