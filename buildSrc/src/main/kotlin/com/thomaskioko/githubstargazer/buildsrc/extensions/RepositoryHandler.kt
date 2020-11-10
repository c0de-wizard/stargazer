package com.thomaskioko.githubstargazer.buildsrc.extensions

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.applyDefault() {
    google()
    jcenter()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://jitpack.io")
    maven("https://maven.fabric.io/public")
    maven("https://maven.google.com")
    maven("http://oss.jfrog.org/artifactory/oss-snapshot-local/")
}
