import com.thomaskioko.stargazers.dependencies.Dependencies

plugins {
    `android-feature-plugin`
}

dependencies {

    implementation(project(":design:common-ui-compose"))

    implementation(Dependencies.AndroidX.dataStore)
    implementation(Dependencies.AndroidX.preferences)

}
