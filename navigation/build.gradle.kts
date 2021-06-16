@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.util.libs

plugins {
    `android-library-plugin`
}

dependencies {

    implementation(project(":design:common-ui-compose"))

    implementation(libs.compose.runtime)
    implementation(libs.compose.navigation)
    implementation(libs.hilt.compose.navigation)
}
