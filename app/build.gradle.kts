plugins {
    `android-app-plugin`
}

dependencies {

    implementation(project(":core"))
    implementation(project(":design:common-ui"))
    implementation(project(":design:common-ui-compose"))
    implementation(project(":repository"))
    implementation(project(":navigation"))
    implementation(project(":features:browse"))
    implementation(project(":features:trending-repositories"))
    implementation(project(":features:bookmarks"))
    implementation(project(":features:repo-details"))
    implementation(project(":features:settings"))
}
