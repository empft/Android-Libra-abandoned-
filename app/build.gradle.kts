plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
    kotlin("plugin.serialization") version "1.5.30"
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.example.libraandroid"
        minSdk = 28
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-alpha04"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.30")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.annotation:annotation:1.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Jetpack Compose

    // Compiler
    implementation("androidx.compose.compiler:compiler:1.1.0-alpha04")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.3.1")
    // Compose Material Design
    implementation("androidx.compose.material:material:1.0.2")
    // Animations
    implementation("androidx.compose.animation:animation:1.0.2")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.0.2")
    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.0.2")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0-beta01")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.2")

    // Navigation

    implementation("androidx.navigation:navigation-fragment-ktx:2.4.0-alpha09")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.0-alpha09")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.4.0-alpha09")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:2.4.0-alpha09")

    // Navigation Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha09")

    // Lifecycle

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0-beta01")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0-beta01")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-beta01")

    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.0-beta01")

    // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation("androidx.lifecycle:lifecycle-common-java8:2.4.0-beta01")

    // optional - helpers for implementing LifecycleOwner in a Service
    implementation("androidx.lifecycle:lifecycle-service:2.4.0-beta01")

    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    implementation("androidx.lifecycle:lifecycle-process:2.4.0-beta01")
    // optional - ReactiveStreams support for LiveData
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.4.0-beta01")

    // optional - Test helpers for LiveData
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    
    // retrofit - networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Security Library
    implementation("androidx.security:security-crypto:1.0.0")

    // Json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")


    // Logging with Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // For murmurhash3
    implementation("com.google.guava:guava:24.1-jre")


}