@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.util.libs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")

}

android {

    compileSdk = libs.versions.android.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.min.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        useIR = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get().toString()
    }

}

dependencies {
    implementation(project(":design:common-ui"))

    api(libs.compose.activity)
    api(libs.compose.foundation)
    api(libs.compose.iconsExtended)
    api(libs.compose.material)
    api(libs.compose.navigation)
    api(libs.compose.tooling)
    api(libs.compose.ui)
    api(libs.compose.uiUtil)
    api(libs.compose.paging)
    api(libs.compose.runtime)
    api(libs.compose.viewModel)
    api(libs.compose.coil)
    api(libs.compose.insets)
    api(libs.compose.accompanist.google.coil)
    api(libs.compose.accompanist.google.insets)
    api(libs.hilt.compose.navigation)
}
