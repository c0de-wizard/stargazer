@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.dependencies.Dependencies
import dependencies.BuildVersions
import dependencies.DependencyVersions
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")

}

android {

    compileSdkVersion(BuildVersions.compileSdkVersion)

    defaultConfig {
        minSdkVersion(BuildVersions.minSdkVersion)

        versionCode = BuildVersions.versionCode
        versionName(BuildVersions.versionName)
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
        kotlinCompilerExtensionVersion = DependencyVersions.compose
    }

}

dependencies {
    implementation(project(":design:common-ui"))
    implementation(project(":design:common-ui-resources"))


    api(Dependencies.AndroidX.Compose.activity)
    api(Dependencies.AndroidX.Compose.foundation)
    api(Dependencies.AndroidX.Compose.iconsExtended)
    api(Dependencies.AndroidX.Compose.material)
    api(Dependencies.AndroidX.Compose.tooling)
    api(Dependencies.AndroidX.Compose.ui)
    api(Dependencies.AndroidX.Compose.uiUtil)
    api(Dependencies.AndroidX.Compose.paging)
    api(Dependencies.AndroidX.Compose.runtime)
    api(Dependencies.AndroidX.Compose.viewModel)
    api(Dependencies.AndroidX.Compose.Accompanist.coil)
    api(Dependencies.AndroidX.Compose.Accompanist.insets)
    api(Dependencies.AndroidX.Compose.Accompanist.Google.coil)
    api(Dependencies.AndroidX.Compose.Accompanist.Google.insets)
}
