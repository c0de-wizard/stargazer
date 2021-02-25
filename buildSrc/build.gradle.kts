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
    languageVersion = PluginsVersions.KOTLIN
}

object PluginsVersions {
    const val ANDROID_GRADLE = "7.0.0-alpha07"
    const val KOTLIN = "1.4.30"
    const val NAVIGATION = "2.3.2"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.ANDROID_GRADLE}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("org.owasp:dependency-check-gradle:1.4.5.1")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${PluginsVersions.KOTLIN}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:${PluginsVersions.NAVIGATION}")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.31.2-alpha")
}
