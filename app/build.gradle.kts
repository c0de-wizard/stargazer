plugins {
    `android-app-plugin`
}

dependencies {

    implementation(project(":core"))
    implementation(project(":repository"))
    implementation(project(":navigation"))
    implementation(project(":features:browse"))
    implementation(project(":features:browse-mvi"))
    implementation(project(":features:bookmarks"))
    implementation(project(":features:repo-details"))

}