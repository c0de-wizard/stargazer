import com.thomaskioko.stargazers.dependencies.Dependencies

plugins {
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(Dependencies.Coroutines.android)

    api(Dependencies.Testing.junit)
    api(Dependencies.Testing.truth)
    api(Dependencies.Testing.turbine)
    api(Dependencies.Testing.Mockito.android)
    api(Dependencies.Testing.Coroutines.test)
}
