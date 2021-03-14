@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.dependencies.Dependencies
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.kotlin

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("de.mannodermaus.android-junit5")
}

android {

    defaultConfig {
        applicationId = "com.thomaskioko.stargazer"
        minSdkVersion(BuildVersions.minSdkVersion)
        compileSdkVersion(BuildVersions.compileSdkVersion)
        targetSdkVersion(BuildVersions.targetSdkVersion)

        versionCode = BuildVersions.versionCode
        versionName = BuildVersions.versionName
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
    }

    lint {
        lintConfig = rootProject.file(".lint/config.xml")
        isCheckAllWarnings = true
        isWarningsAsErrors = true
        isAbortOnError = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = DependencyVersions.compose
        kotlinCompilerVersion = PluginsVersions.kotlin
    }

    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

dependencies {

    testImplementation(project(":common-testing"))
    androidTestImplementation(project(":common-testing"))

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.dataStore)


    implementation(Dependencies.AndroidX.Compose.activity)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.tooling)
    implementation(Dependencies.AndroidX.Compose.ui)

    implementation(Dependencies.AndroidX.Lifecycle.runtime)

    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ktx)
    implementation(Dependencies.AndroidX.Navigation.runtime)

    implementation(Dependencies.Google.Hilt.core)
    implementation(Dependencies.Google.Hilt.viewmodel)
    kapt(Dependencies.Google.Hilt.compiler)

    implementation(Dependencies.Google.material)
    implementation(Dependencies.timber)
    implementation(Dependencies.leakCanary)

    implementation(Dependencies.Retrofit.Moshi.core)
    implementation(Dependencies.Retrofit.moshiConverter)
    kapt(Dependencies.Retrofit.Moshi.kapt)

    implementation(Dependencies.OkHttp.loggingInterceptor)

    implementation(Dependencies.Room.roomKtx)
}
