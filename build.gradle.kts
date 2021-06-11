import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories.applyDefault()
}

plugins {
    id("com.vanniktech.android.junit.jacoco") version "0.16.0"
}

allprojects {
    repositories.applyDefault()

    plugins.apply("checks.ktlint")
    plugins.apply("checks.detekt")
    plugins.apply("checks.spotless")
    plugins.apply("checks.dependency-updates")
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        with(kotlinOptions) {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            useIR = true
            languageVersion = "1.5"
            apiVersion = "1.5"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=kotlin.ExperimentalStdlibApi",
                "-Xopt-in=kotlin.time.ExperimentalTime",
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xopt-in=kotlin.contracts.ExperimentalContracts",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            )
        }
    }
}
