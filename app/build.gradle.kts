import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.daggerHiltAndroid)
}

android {
    namespace = "dev.arch3rtemp.tvshows"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.arch3rtemp.tvshows"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localProperties = Properties()
        FileInputStream(rootProject.file("local.properties")).use {
            localProperties.load(it)
        }
        buildConfigField("String", "BEARER_TOKEN", "\"${localProperties.getProperty("bearer_token")}\"")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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

    implementation(project(":common-ui"))
    implementation(project(":common-data"))
    implementation(project(":navigation"))
    implementation(project(":feature-tvshow"))

    // hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    ksp(libs.room.compiler)
}