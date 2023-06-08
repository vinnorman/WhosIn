@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library)
    id("org.jetbrains.kotlin.android")
}

android {

    namespace = "com.gonativecoders.whosin.core.data"

    compileSdk = 33

    defaultConfig {
        minSdk = 23
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }


}

dependencies {
    implementation(project(":core:auth"))
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    implementation("androidx.datastore:datastore-preferences:1.0.0")


    implementation("io.insert-koin:koin-android:3.2.2")
}