import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = PluginsVersions.kotlin
}

object PluginsVersions {
    const val androidGradle = "7.0.0-alpha08"
    const val kotlin = "1.4.30"
    const val navigaiton = "2.3.2"
    const val daggerHiltAndroid: String = "2.31.2-alpha"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.androidGradle}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.kotlin}")
    implementation("org.owasp:dependency-check-gradle:1.4.5.1")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${PluginsVersions.kotlin}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:${PluginsVersions.navigaiton}")
    implementation("com.google.dagger:hilt-android-gradle-plugin:${PluginsVersions.daggerHiltAndroid}")
}
