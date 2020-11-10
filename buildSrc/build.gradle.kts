plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    jcenter()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://maven.fabric.io/public")
    maven("https://plugins.gradle.org/m2/")
}

object PluginsVersions {
    const val ANDROID_GRADLE = "4.1.0-rc03"
    const val KOTLIN = "1.4.10"
}


dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.ANDROID_GRADLE}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("org.owasp:dependency-check-gradle:1.4.5.1")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${PluginsVersions.KOTLIN}")
}
