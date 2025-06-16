plugins {
    id("android-library-conventions")
    id("android-compose-conventions")
    id("android-hilt-conventions")
}

android {
    namespace = "com.mehdiatique.feature.assistant"
}

dependencies {
    implementation(project(":core:ui-contract"))
    implementation(project(":design-system"))
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
