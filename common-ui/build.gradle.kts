plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.daggerHiltAndroid)
}

android {
    namespace = "dev.arch3rtemp.common_ui"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        viewBinding = true
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

    // androidx
    api(libs.core.ktx)
    api(libs.appcompat)
    api(libs.fragment.ktx)
    api(libs.swiperefreshlayout)
    api(libs.core.splashscreen)
    api(libs.pallete)

    // google
    api(libs.material)

    // dagger2
//    implementation(libs.dagger2)
//    ksp(libs.dagger2.compiler)

    // hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    // coil
    api(libs.coil)

    // timber
    api(libs.timber)
}