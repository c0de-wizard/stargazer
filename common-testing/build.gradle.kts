import com.thomaskioko.stargazers.dependencies.Dependencies

plugins {
    `kotlin-library`
}

dependencies {
    api(Dependencies.Coroutines.android)
    api(Dependencies.Testing.Coroutines.test)

    api(Dependencies.Testing.truth)
    api(Dependencies.Testing.assertJ)
    api(Dependencies.Testing.turbine)
    api(Dependencies.Testing.mockitoKotlin)
    api(Dependencies.Testing.robolectric)

    api(Dependencies.Testing.junit)
    // TODO:: Remove truth & vintage dependency after migrating to junit5
    api(Dependencies.Testing.Junit.api)
    api(Dependencies.Testing.Junit.params)
    api(Dependencies.Testing.Junit.engine)
    api(Dependencies.Testing.Junit.vintage)

    api(Dependencies.Testing.Mockito.core)
    api(Dependencies.Testing.Mockito.android)

    api(Dependencies.Testing.Espresso.core)

    api(Dependencies.Testing.AndroidX.core)
    api(Dependencies.Testing.AndroidX.junit)
    api(Dependencies.Testing.AndroidX.rules)
    api(Dependencies.Testing.AndroidX.runner)
}
