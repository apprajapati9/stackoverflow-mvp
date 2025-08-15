plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.junit5.plugin)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.apprajapati.mvp_stackoverflow"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.apprajapati.mvp_stackoverflow"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_20
        targetCompatibility = JavaVersion.VERSION_20
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_20.toString()
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.material3)

    //Retrofit..
    implementation(libs.retrofit)
    implementation(libs.retrofitGsonConverter)
    implementation(libs.retrofitLogging)

    debugImplementation(libs.leak.canary)

    implementation(libs.ktx.coroutines)

//    //Test related.
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.square.assertj)
    testRuntimeOnly(libs.junit.jupiter.engine)

    //lifecycle
    implementation(libs.lifecycle.runtime.ktx)

    //Testing flow
    testImplementation(libs.app.cash.turbine) //library that makes testing flows extremely easy
    testImplementation(libs.com.google.truth) //better assertions than junit.
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    // comes with testing functionality that we need to test.

    implementation(libs.google.code.gson)

    //Compose related dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.tooling.preview)

}