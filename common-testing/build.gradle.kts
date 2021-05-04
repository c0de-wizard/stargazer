plugins {
    `kotlin-library`
}

dependencies {
    api(libs.coroutines.android)
    api(libs.testing.coroutines.test)

    api(libs.testing.truth)
    api(libs.testing.assertJ)
    api(libs.testing.turbine)
    api(libs.testing.mockitoKotlin)
    api(libs.testing.robolectric)

    api(libs.testing.junit)
    // TODO:: Remove truth & vintage dependency after migrating to junit5
    api(libs.testing.junit5.api)
    api(libs.testing.junit5.params)
    api(libs.testing.junit5.engine)
    api(libs.testing.junit5.vintage)

    api(libs.testing.mockito.core)
    api(libs.testing.mockito.android)

    api(libs.testing.espresso.core)

    api(libs.testing.androidx.core)
    api(libs.testing.androidx.junit)
    api(libs.testing.androidx.rules)
    api(libs.testing.androidx.runner)
}
