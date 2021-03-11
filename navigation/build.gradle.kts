import com.thomaskioko.stargazers.dependencies.Dependencies

plugins {
    `android-library-plugin`
    id("androidx.navigation.safeargs.kotlin")
}

dependencies {

    implementation(Dependencies.AndroidX.Navigation.ktx)
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.runtime)
}
