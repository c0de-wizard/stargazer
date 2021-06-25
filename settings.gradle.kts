enableFeaturePreview("VERSION_CATALOGS")
rootProject.name = "Stargazer"

include(
    ":app",
    ":core",
    ":common-testing",
    ":navigation",
    ":repository",
    ":features:bookmarks",
    ":features:search",
    ":features:trending-repositories",
    ":features:repo-details",
    ":features:settings",
    ":common-testing",
    ":design:common-ui-compose",
)
