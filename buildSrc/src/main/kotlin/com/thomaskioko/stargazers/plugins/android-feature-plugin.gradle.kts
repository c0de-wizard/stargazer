@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.dependencies.Dependencies
import dependencies.BuildVersions
import dependencies.DependencyVersions
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
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


    composeOptions {
        kotlinCompilerExtensionVersion = DependencyVersions.compose
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {

    implementation(project(":core"))
    implementation(project(":design:common-ui"))
    implementation(project(":navigation"))

    testImplementation(project(":common-testing"))
    androidTestImplementation(project(":common-testing"))

    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.constraintLayout)

    implementation(Dependencies.AndroidX.Compose.activity)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.tooling)
    implementation(Dependencies.AndroidX.Compose.ui)

    implementation(Dependencies.AndroidX.Lifecycle.runtime)
    implementation(Dependencies.AndroidX.Lifecycle.viewmodel)

    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ktx)
    implementation(Dependencies.AndroidX.Navigation.runtime)

    implementation(Dependencies.Google.material)
    implementation(Dependencies.timber)

    implementation(Dependencies.Coroutines.android)

    implementation(Dependencies.Google.Hilt.core)
    implementation(Dependencies.Google.Hilt.viewmodel)
    kapt(Dependencies.Google.Hilt.compiler)

    androidTestImplementation(Dependencies.Testing.AndroidX.junit)
    androidTestImplementation(Dependencies.Testing.AndroidX.junitKtx)
    androidTestImplementation(Dependencies.Testing.AndroidX.fragment)
    androidTestImplementation(Dependencies.Testing.AndroidX.Compose.ui)
    androidTestImplementation(Dependencies.Google.Hilt.core)
    androidTestImplementation(Dependencies.Testing.Hilt.androidTesting)
    kaptAndroidTest(Dependencies.Google.Hilt.compiler)
}
