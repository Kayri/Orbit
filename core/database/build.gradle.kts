plugins {
    id("ksp-conventions")
    id("android-library-conventions")
    id("android-compose-conventions")
    id("android-hilt-conventions")
}

android {
    namespace = "com.mehdiatique.core.database"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}