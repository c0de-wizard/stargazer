plugins {
    `kotlin-library`
}

dependencies {
    api(Dependencies.Testing.junit)
    api(Dependencies.Testing.truth)
    api(Dependencies.Testing.Coroutines.test)
}
