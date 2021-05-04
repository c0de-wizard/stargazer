@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.util.libs

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
        minSdk = libs.versions.android.min.get().toInt()
        compileSdk = libs.versions.android.compile.get().toInt()
        targetSdk = libs.versions.android.target.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = true
            //versionNameSuffix = "-DEBUG"

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

    packagingOptions {
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        resources.excludes.add("DebugProbesKt.bin")
    }


    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get().toString()
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

    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.constraintLayout)

    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.compose.tooling)
    implementation(libs.compose.ui)

    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ktx)
    implementation(libs.navigation.runtime)

    implementation(libs.material)
    implementation(libs.timber)

    implementation(libs.coroutines.android)

    implementation(libs.hilt.core)
    implementation(libs.hilt.viewmodel)
    kapt(libs.hilt.compiler)

    androidTestImplementation(libs.testing.androidx.junit)
    androidTestImplementation(libs.testing.androidx.junitktx)
    androidTestImplementation(libs.testing.compose.ui)
    androidTestImplementation(libs.hilt.core)
    androidTestImplementation(libs.testing.hilt)
    kaptAndroidTest(libs.hilt.compiler)
}
