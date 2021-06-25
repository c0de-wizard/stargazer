import com.thomaskioko.stargazers.util.libs

plugins {
    `android-library-plugin`
}

dependencies {

    implementation(libs.timber)
    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.coreKtx)
    implementation(libs.lifecycle.common)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.compose.runtime)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)

    implementation(libs.coroutines.android)
}
