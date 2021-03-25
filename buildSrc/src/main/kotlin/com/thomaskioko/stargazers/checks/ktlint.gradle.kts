package checks

import dependencies.DependencyVersions

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:${DependencyVersions.ktLint}")
}

tasks {
    register<JavaExec>("ktlint") {
        group = "VERIFICATION"
        description = "Check Kotlin code style."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("--android", "src/**/*.kt")
    }

    register<JavaExec>("ktlintFormat") {
        group = "FORMATTING"
        description = "Fix Kotlin code style deviations."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("--android", "-F", "src/**/*.kt")
    }
}
