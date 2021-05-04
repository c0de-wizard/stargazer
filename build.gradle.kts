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
            freeCompilerArgs += "-Xuse-experimental=" +
                    "kotlin.RequiresOptIn," +
                    "kotlin.Experimental," +
                    "kotlin.time.ExperimentalTime," +
                    "kotlin.ExperimentalStdlibApi," +
                    "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                    "kotlinx.coroutines.InternalCoroutinesApi," +
                    "kotlinx.coroutines.ObsoleteCoroutinesApi," +
                    "kotlinx.coroutines.FlowPreview"
        }
    }
}
