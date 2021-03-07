import org.gradle.api.JavaVersion

const val kotlinVersion = "1.4.30"

object BuildVersions {
    val minSdkVersion = 21
    val targetSdkVersion = 30
    val compileSdkVersion = 30
    val buildToolsVersion = "30.0.3"
    val versionCode = 1
    val versionName = "0.1"
    val javaVersion = JavaVersion.VERSION_1_8
}

object Dependencies {

    val timber = "com.jakewharton.timber:timber:4.7.1"
    val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.6"

    object AndroidX {
        val appCompat = "androidx.appcompat:appcompat:1.3.0-beta01"
        val coreKtx = "androidx.core:core-ktx:1.5.0-beta02"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.0-alpha2"
        val dataStore = "androidx.datastore:datastore-preferences:1.0.0-alpha07"

        object Lifecycle {
            private const val version = "2.3.0"
            val common = "androidx.lifecycle:lifecycle-common-java8:$version"
            val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Navigation {
            private const val version = "2.3.3"
            val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            val runtime = "androidx.navigation:navigation-runtime-ktx:$version"
            val ktx = "androidx.navigation:navigation-ui-ktx:$version"
        }
    }

    object Coroutines {
        const val version = "1.4.3"
        val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        val reactive = "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:$version"
    }

    object Google {

        val material = "com.google.android.material:material:1.4.0-alpha01"

        object Hilt {
            val version = "2.33-beta"
            val core = "com.google.dagger:hilt-android:$version"
            val compiler = "com.google.dagger:hilt-compiler:$version"
            val viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
        }
    }

    object OkHttp {
        private const val version = "5.0.0-alpha.2"
        val okhttp = "com.squareup.okhttp3:okhttp:$version"
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        val retrofit = "com.squareup.retrofit2:retrofit:$version"
        val retrofitCoroutinesConverter =
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"

        object Moshi {
            private const val moshiVersion = "1.11.0"
            val core = "com.squareup.moshi:moshi:$moshiVersion"
            val kapt = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
            val converter = "com.squareup.retrofit2:converter-moshi:2.9.0"
        }
    }

    object Room {
        private const val version = "2.3.0-beta02"
        val roomKtx = "androidx.room:room-ktx:$version"
        val compiler = "androidx.room:room-compiler:$version"
    }

    object Testing {
        val assertJ = "org.assertj:assertj-core:3.19.0"
        val junit = "junit:junit:4.13.2"
        val truth = "com.google.truth:truth:1.1.2"
        val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
        val turbine = "app.cash.turbine:turbine:0.4.0"
        val robolectric: String = "org.robolectric:robolectric:4.5.1"
        val archCoreTest: String = "android.arch.core:core-testing:1.1.1"

        object AndroidX {
            val junit = "androidx.test.ext:junit:1.1.3-alpha04"
            val core = "androidx.arch.core:core-testing:2.1.0"
            val fragment = "androidx.fragment:fragment-testing:1.3.0"
            val rules: String = "androidx.test:rules:1.4.0-alpha04"
            val runner: String = "androidx.test:runner:1.4.0-alpha04"
        }

        object Coroutines {
            val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Dependencies.Coroutines.version}"
        }

        object Espresso {
            val version: String = "3.4.0-alpha04"
            val core: String = "androidx.test.espresso:espresso-core:$version"
            val contrib: String = "androidx.test.espresso:espresso-contrib:$version"
        }

        object Junit {
            const val version: String = "5.8.0-M1"
            val api = "org.junit.jupiter:junit-jupiter-api:$version"
            val engine = "org.junit.jupiter:junit-jupiter-engine:$version"
            val params = "org.junit.jupiter:junit-jupiter-params:$version"
            val runner =" org.junit.platform:junit-platform-runner:1.3.2"

            //TODO:: Remove this dependency after migrating to junit5
            val vintage = "org.junit.vintage:junit-vintage-engine:$version"
        }

        object Hilt {
            val androidTesting = "com.google.dagger:hilt-android-testing:${Google.Hilt.version}"
        }

        object Mockito {
            val android = "org.mockito:mockito-android:3.8.0"
            val core = "org.mockito:mockito-core:3.8.0"
        }
    }
}
