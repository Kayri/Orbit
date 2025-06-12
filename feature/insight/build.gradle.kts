plugins {
    id("android-library-conventions")
    id("android-compose-conventions")
    id("android-hilt-conventions")
}

android {
    namespace = "com.mehdiatique.feature.insight"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:navigation-contract"))
    implementation(project(":core:ui-contract"))
    implementation(project(":design-system"))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
