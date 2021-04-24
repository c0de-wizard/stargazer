@file:Suppress("UnstableApiUsage")

import org.gradle.kotlin.dsl.kotlin
import dependencies.BuildVersions

plugins {
    id("com.android.library")
    kotlin("android")
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

}
