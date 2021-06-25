@file:Suppress("UnstableApiUsage")

import com.thomaskioko.stargazers.util.libs

plugins {
    `android-library-plugin`
}

dependencies {

    implementation(project(":core"))
    testImplementation(project(":common-testing"))

    implementation(libs.androidx.paging)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.coroutines)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.timber)

    implementation(libs.moshi.core)
    kapt(libs.moshi.kapt)

    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
}
