@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://kotlin.bintray.com/kotlinx")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = libs.versions.kotlin.get().toString()
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.kotlin.gradle)
    implementation(libs.kotlin.allopen)
    implementation(libs.android.gradle)
    implementation(libs.plugins.navigation)
    implementation(libs.plugins.hilt)
    implementation(libs.plugins.ktlint)
    implementation(libs.plugins.spotless)
    implementation(libs.plugins.detekt)
    implementation(libs.plugins.android.junit)
    implementation(libs.plugins.dependency.check)
}
