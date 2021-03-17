@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.dependencies.Dependencies
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getting
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
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = DependencyVersions.compose
    }

}

dependencies {
    implementation(Dependencies.AndroidX.Compose.accompanistCoil)
    implementation(Dependencies.AndroidX.Compose.activity)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.tooling)
    implementation(Dependencies.AndroidX.Compose.ui)
    implementation(Dependencies.AndroidX.Compose.iconsExtended)
}
