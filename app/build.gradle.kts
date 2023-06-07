@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    alias(libs.plugins.android.application)

    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

@Suppress("UnstableApiUsage")
android {
    compileSdk = 33

    namespace = "com.gonativecoders.whosin"

    task("checkVersionName") {
        println(generateVersionName())
        println(generateVersionCode())
    }

    defaultConfig {
        applicationId = "com.gonativecoders.whosin"
        minSdk = 24
        targetSdk = 33
        versionCode = generateVersionCode()
        versionName = generateVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {

            val keystoreProperties = Properties().apply {
                val keystorePropertiesFile = File("keystore.properties")
                if (keystorePropertiesFile.exists()) {
                    load(keystorePropertiesFile.reader())
                } else {
                    put("storeFile", System.getenv("KEYSTORE_PATH") ?: "../keystore.jks")
                    put("storePassword", System.getenv("KEYSTORE_PASSWORD") ?: "devkeystore")
                    put("keyAlias", System.getenv("KEY_ALIAS") ?: "whosindev")
                    put("keyPassword", System.getenv("KEY_PASSWORD") ?: "whosindevpassword")
                }
            }

            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    tasks.withType(KotlinCompile::class).configureEach {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        }
    }
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:auth"))

    implementation(libs.androidx.ktx)

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.compose.ui:ui:1.5.0-alpha02")
    implementation("androidx.compose.material3:material3:1.1.0-beta02")
    implementation("androidx.compose.ui:ui-tooling:1.5.0-alpha03")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0-alpha03")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.compose.material:material-icons-extended:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    implementation("io.coil-kt:coil-compose:2.3.0")

    implementation("androidx.activity:activity-ktx:1.2.3")
    implementation("androidx.fragment:fragment-ktx:1.3.3")

    implementation("androidx.camera:camera-camera2:1.3.0-alpha07")
    implementation("androidx.camera:camera-lifecycle:1.3.0-alpha07")
    implementation("androidx.camera:camera-view:1.3.0-alpha07")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.6.2")

    implementation("com.google.accompanist:accompanist-permissions:0.25.0")

    implementation("io.insert-koin:koin-android:3.2.2")
    implementation("io.insert-koin:koin-androidx-compose:3.2.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.firebase.crashlytics.ktx)

    implementation("com.google.android.gms:play-services-auth:20.5.0")



    testImplementation("junit:junit:4.13.2")
    testImplementation("io.insert-koin:koin-test:3.2.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0-alpha03")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0-alpha03")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.0-alpha03")
}