@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.dependencies.Dependencies
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("jacoco")
    id("plugins.jacoco-report")
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

    testImplementation(Dependencies.Testing.junit)
    // TODO:: Remove truth & vintage dependency after migrating to junit5
    testImplementation(Dependencies.Testing.truth)
    testRuntimeOnly(Dependencies.Testing.Junit.vintage)

    testImplementation(Dependencies.Testing.assertJ)

    testImplementation(Dependencies.Testing.Junit.api)
    testImplementation(Dependencies.Testing.Junit.params)
    testRuntimeOnly(Dependencies.Testing.Junit.engine)

    testImplementation(Dependencies.Testing.mockitoKotlin)
    testImplementation(Dependencies.Testing.turbine)
    testImplementation(Dependencies.Testing.Coroutines.test)
    testImplementation(Dependencies.Testing.AndroidX.core)
    testImplementation(Dependencies.Testing.robolectric)

    // AndroidJUnitRunner and JUnit Rules
    testImplementation(Dependencies.Testing.AndroidX.junit)
    testImplementation(Dependencies.Testing.AndroidX.rules)
    testImplementation(Dependencies.Testing.AndroidX.runner)

    androidTestImplementation(Dependencies.Testing.AndroidX.junit)
    androidTestImplementation(Dependencies.Testing.Espresso.core)
}
