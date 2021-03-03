import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.kotlin

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
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
            isMinifyEnabled = false
            versionNameSuffix = "-DEBUG"
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
}

dependencies {

    implementation(Dependencies.Kotlin.stdlib)

    implementation(Dependencies.Google.Hilt.core)
    implementation(Dependencies.Google.Hilt.viewmodel)
    kapt(Dependencies.Google.Hilt.compiler)

    testImplementation(Dependencies.Testing.junit)
    testImplementation(Dependencies.Testing.truth)
    testImplementation(Dependencies.Testing.mockitoKotlin)
    testImplementation(Dependencies.Testing.turbine)
    testImplementation(Dependencies.Testing.Coroutines.test)

    androidTestImplementation(Dependencies.Testing.androidJunit)
    androidTestImplementation(Dependencies.Testing.Espresso.core)
}
