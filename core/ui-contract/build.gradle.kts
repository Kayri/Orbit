plugins {
    id("android-library-conventions")
    id("android-compose-conventions")
}

android {
    namespace = "com.mehdiatique.core.ui_contract"
}

dependencies {
    implementation(libs.androidx.material3)
}