plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.daggerHiltAndroid)
}

android {
    namespace = "dev.arch3rtemp.common_data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {

    api(project(":common"))

    // hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    // retrofit
    api(libs.retrofit2)
    api(libs.converter.moshi)

    // room
    api(libs.room.runtime)
    ksp(libs.room.compiler)
    api(libs.room.ktx)
    api(libs.room.paging)

    // moshi
    api(libs.moshi)
    api(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
}