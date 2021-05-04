@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.util.libs

plugins {
    `android-library-plugin`
    id("androidx.navigation.safeargs.kotlin")
}

dependencies {

    implementation(libs.navigation.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.runtime)
}
