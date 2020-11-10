import com.thomaskioko.githubstargazer.buildsrc.extensions.applyDefault
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


// all projects = root project + sub projects
allprojects {
    repositories.applyDefault()

    afterEvaluate {
        tasks.withType<JavaCompile>().configureEach { options.compilerArgs.addAll(arrayOf("-Xmaxerrs", "500")) }
    }
}

subprojects {
    tasks.withType<Test> {
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    }
}
// JVM target applied to all Kotlin tasks across all sub-projects
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

