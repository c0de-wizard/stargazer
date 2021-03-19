import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories.applyDefault()
}

plugins {
    id("com.vanniktech.android.junit.jacoco") version DependencyVersions.jacoco
}

allprojects {
    repositories.applyDefault()

    plugins.apply("plugins.ktlint")
    plugins.apply("plugins.detekt")
    plugins.apply("plugins.spotless")
    plugins.apply("plugins.dependency-updates")

    configurations.all {
        resolutionStrategy.force("org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersions.coroutines}")
        resolutionStrategy.force("org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersions.coroutines}")
        resolutionStrategy.force("org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependencyVersions.coroutines}")

        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion(PluginsVersions.kotlin)
            }
        }
    }
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
