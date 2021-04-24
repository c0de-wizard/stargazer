import com.thomaskioko.stargazers.dependencies.Dependencies

plugins {
    `android-library-plugin`
}

dependencies {

    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.Google.material)
}
