plugins {
    id("android-library-conventions")
}

android {
    namespace = "com.mehdiatique.llamawrapper"

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    defaultConfig {
        ndk {
            abiFilters += listOf("arm64-v8a") // You can add others later
        }
    }
}

dependencies {
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.material.icons.extended)
//    implementation(libs.androidx.palette)

//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}