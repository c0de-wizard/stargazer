@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.dependencies.Dependencies
import dependencies.BuildVersions
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("jacoco")
    id("checks.jacoco-report")
    id("de.mannodermaus.android-junit5")
}

android {
    defaultConfig {
        minSdkVersion(BuildVersions.minSdkVersion)
        compileSdkVersion(BuildVersions.compileSdkVersion)
        targetSdkVersion(BuildVersions.targetSdkVersion)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = true
            versionNameSuffix = "-DEBUG"

            isTestCoverageEnabled = true
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    sourceSets {
        val androidTest by getting
        val test by getting
        androidTest.java.srcDirs("src/androidTest/kotlin")
        test.java.srcDirs("src/test/kotlin")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    lint {
        lintConfig = rootProject.file(".lint/config.xml")
        isCheckAllWarnings = true
        isWarningsAsErrors = true
        isAbortOnError = false
    }

    packagingOptions {
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        resources.excludes.add("DebugProbesKt.bin")
    }
}

dependencies {

    testImplementation(project(":common-testing"))
    androidTestImplementation(project(":common-testing"))

    implementation(Dependencies.Google.Hilt.core)
    implementation(Dependencies.Google.Hilt.viewmodel)
    kapt(Dependencies.Google.Hilt.compiler)
}
