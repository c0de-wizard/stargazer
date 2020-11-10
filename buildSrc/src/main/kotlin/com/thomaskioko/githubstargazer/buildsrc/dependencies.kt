package com.thomaskioko.githubstargazer.buildsrc

import org.gradle.api.JavaVersion

object BuildVersions {
    val minSdkVersion = 17
    val targetSdkVersion = 30
    val compileSdkVersion = 30
    val buildToolsVersion = "30.0.2"
    val versionCode = 1
    val versionName = "0.1"
    val javaVersion = JavaVersion.VERSION_1_8
}

object Dependencies {

    val material = "com.google.android.material:material:1.2.1"

    object AndroidX {
        val appCompat = "androidx.appcompat:appcompat:1.1.0"
        val coreKtx = "androidx.core:core-ktx:1.3.2"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    }

    object Kotlin {
        private const val version = "1.4.10"
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    }

    object Testing {
        val junit = "junit:junit:4.12"
        val androidJunit = "androidx.test.ext:junit:1.1.2"
        val androidEspressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}
