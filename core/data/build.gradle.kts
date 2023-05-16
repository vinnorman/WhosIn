@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application)

    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {

    compileSdk = 33

    namespace = "com.gonativecoders.whosin.core.data"

}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

}