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

        object Lifecycle {
            private const val version = "2.2.0"
            val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }

    object Coroutines {
        private const val version = "1.4.1"
        val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        val reactive = "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$version"
    }

    object Dagger {
        private const val version = "2.28"
        val android = "com.google.dagger:dagger-android:$version"
        val core = "com.google.dagger:dagger:$version"
        val compiler = "com.google.dagger:dagger-compiler:$version"
        val processor = "com.google.dagger:dagger-android-processor:$version"
        val support = "com.google.dagger:dagger-android-support:$version"
    }

    object Kotlin {
        private const val version = "1.4.10"
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    }

    object OkHttp {
        private const val version = "3.10.0"
        val okhttp = "com.squareup.okhttp3:okhttp:$version"
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private const val version = "2.6.0"
        val retrofit = "com.squareup.retrofit2:retrofit:$version"
        val retrofitCoroutinesConverter =
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"

        object Moshi {
            private const val moshiVersion = "1.9.3"
            val core = "com.squareup.moshi:moshi:$moshiVersion"
            val kapt = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
            val converter = "com.squareup.retrofit2:converter-moshi:2.4.0"
        }
    }

    object Room {
        private const val version = "2.3.0-alpha02"
        val roomKtx = "androidx.room:room-ktx:$version"
        val compiler = "androidx.room:room-compiler:$version"
    }

    object Testing {
        val junit = "junit:junit:4.12"
        val androidJunit = "androidx.test.ext:junit:1.1.2"
        val androidEspressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        val truth = "com.google.truth:truth:1.0.1"
        val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"

        object AndroidX {
            val junit = "androidx.test.ext:junit:1.1.2"
            val core = "androidx.arch.core:core-testing:2.1.0"
        }

        object Coroutines {
            val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.2"
        }

        object Mockito {
            val core = "org.mockito:mockito-core:2.25.1"
        }
    }
}
