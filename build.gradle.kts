@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application) apply false

    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("com.google.gms.google-services") version "4.3.14" apply false
    id("com.google.firebase.crashlytics") version "2.9.5" apply false
}
