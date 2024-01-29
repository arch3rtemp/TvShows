plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "dev.arch3rtemp.common_ui"
    compileSdk = 34

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

    // shimmer
    api(libs.shimmer)

    // retrofit
    api(libs.retrofit2)
    api(libs.converter.moshi)

    // dagger2
//    implementation(libs.dagger2)
//    ksp(libs.dagger2.compiler)

    // hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    // room
    api(libs.room.runtime)
    ksp(libs.room.compiler)
    api(libs.room.ktx)
    api(libs.room.paging)

    // moshi
    api(libs.moshi)
    api(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // coil
    api(libs.coil)

    // timber
    api(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}