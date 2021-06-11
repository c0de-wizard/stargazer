@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.util.libs

plugins {
    `android-library-plugin`
}

dependencies {

    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.coreKtx)
    implementation(libs.material)
}
