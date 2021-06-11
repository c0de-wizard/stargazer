@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.util.libs

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
}

android {

    defaultConfig {
        applicationId = "com.thomaskioko.stargazer"
        minSdk = libs.versions.android.min.get().toInt()
        compileSdk = libs.versions.android.compile.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = false
            versionNameSuffix = "-DEBUG"
            applicationIdSuffix = ".debug"
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    sourceSets {
        val androidTest by getting
        val test by getting
        androidTest.java.srcDirs("src/androidTest/kotlin")
        test.java.srcDirs("src/test/kotlin")
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {

    testImplementation(project(":common-testing"))
    androidTestImplementation(project(":common-testing"))

    implementation(libs.androidx.coreKtx)
    implementation(libs.material)

    implementation(libs.lifecycle.runtime)

    implementation(libs.hilt.core)
    implementation(libs.hilt.viewmodel)
    kapt(libs.hilt.compiler)

    implementation(libs.timber)
    implementation(libs.leakCanary)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.coroutines)
    kapt(libs.moshi.kapt)

    implementation(libs.okhttp.logging)

    implementation(libs.room.ktx)
}
