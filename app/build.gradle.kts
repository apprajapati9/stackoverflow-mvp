plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.junit5.plugin)
}

android {
    namespace = "com.apprajapati.mvp_stackoverflow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apprajapati.mvp_stackoverflow"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

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

}