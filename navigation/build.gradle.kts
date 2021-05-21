@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.util.libs

plugins {
    `android-library-plugin`
    id("androidx.navigation.safeargs.kotlin")
}

dependencies {

    implementation(project(":design:common-ui"))
    implementation(project(":design:common-ui-compose"))

    implementation(libs.navigation.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.runtime)
}
