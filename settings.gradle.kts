rootProject.name = "Stargazer"

include(
    ":app",
    ":core",
    ":common-ui",
    ":common-testing",
    ":navigation",
    ":repository",
    ":features:bookmarks",
    ":features:browse",
    ":features:browse-mvi",
    ":features:repo-details"
)
include(":common-testing")
