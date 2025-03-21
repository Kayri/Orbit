plugins {
    id("android-application-conventions")
    id("android-compose-conventions")
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
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
