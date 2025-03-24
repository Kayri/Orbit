plugins {
    id("android-library-conventions")
    id("android-compose-conventions")
}

android {
    namespace = "com.mehdiatique.orbit.design"
}

dependencies {
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.palette)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}