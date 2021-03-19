import com.thomaskioko.stargazers.dependencies.Dependencies

plugins {
    `android-library-plugin`
}

dependencies {

    implementation(project(":design:common-ui-resources"))

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.Google.material)
}
