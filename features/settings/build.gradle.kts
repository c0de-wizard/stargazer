@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.util.libs

plugins {
    `android-feature-plugin`
}

dependencies {

    implementation(project(":design:common-ui-compose"))

    implementation(libs.androidx.dataStore)
    implementation(libs.androidx.preferences)

}
