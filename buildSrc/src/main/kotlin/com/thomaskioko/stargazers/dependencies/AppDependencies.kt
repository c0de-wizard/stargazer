package com.thomaskioko.stargazers.dependencies

import dependencies.DependencyVersions

object Dependencies {
    const val timber = "com.jakewharton.timber:timber:${DependencyVersions.timber}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${DependencyVersions.leakCanary}"

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${DependencyVersions.appCompat}"
        const val coreKtx = "androidx.core:core-ktx:${DependencyVersions.coreKtx}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${DependencyVersions.constraintLayout}"
        const val dataStore = "androidx.datastore:datastore-preferences:${DependencyVersions.dataStore}"
        const val paging = "androidx.paging:paging-runtime-ktx:${DependencyVersions.paging}"
        const val preferences = "androidx.preference:preference-ktx:${DependencyVersions.preferences}"

        object Compose {
            const val ui = "androidx.compose.ui:ui:${DependencyVersions.compose}"
            const val uiUtil = "androidx.compose.ui:ui-util:${DependencyVersions.compose}"
            const val uiLayout = "androidx.ui:ui-layout:${DependencyVersions.compose}"
            const val tooling = "androidx.compose.ui:ui-tooling:${DependencyVersions.compose}"
            const val foundation = "androidx.compose.foundation:foundation:${DependencyVersions.compose}"
            const val foundationLayout = "androidx.compose.foundation:foundation-layout:${DependencyVersions.compose}"
            const val material = "androidx.compose.material:material:${DependencyVersions.compose}"
            const val iconsCore = "androidx.compose.material:material-icons-core:${DependencyVersions.compose}"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:${DependencyVersions.compose}"
            const val paging = "androidx.paging:paging-compose:${DependencyVersions.pagingCompose}"
            const val runtime = "androidx.compose.runtime:runtime:${DependencyVersions.compose}"
            const val activity = "androidx.activity:activity-compose:${DependencyVersions.composeActivity}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${DependencyVersions.composeViewModel}"


            object Accompanist {
                const val coil = "dev.chrisbanes.accompanist:accompanist-coil:${DependencyVersions.accompanist}"
                const val insets = "dev.chrisbanes.accompanist:accompanist-insets:${DependencyVersions.accompanist}"

                object Google {
                    const val coil = "com.google.accompanist:accompanist-coil:${DependencyVersions.accompanistGoogle}"
                    const val insets = "com.google.accompanist:accompanist-insets:${DependencyVersions.accompanistGoogle}"
                }
            }
        }

        object Lifecycle {
            const val common = "androidx.lifecycle:lifecycle-common-java8:${DependencyVersions.lifecycle}"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersions.lifecycle}"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependencyVersions.lifecycle}"
        }

        object Navigation {
            const val fragment = "androidx.navigation:navigation-fragment-ktx:${DependencyVersions.navigation}"
            const val runtime = "androidx.navigation:navigation-runtime-ktx:${DependencyVersions.navigation}"
            const val ktx = "androidx.navigation:navigation-ui-ktx:${DependencyVersions.navigation}"
        }
    }

    object Coroutines {
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersions.coroutines}"
    }

    object Google {
        const val material = "com.google.android.material:material:${DependencyVersions.material}"

        object Hilt {
            const val core = "com.google.dagger:hilt-android:${DependencyVersions.hilt}"
            const val compiler = "com.google.dagger:hilt-compiler:${DependencyVersions.hilt}"
            const val viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:${DependencyVersions.hiltViewModel}"
        }
    }

    object OkHttp {
        const val okhttp = "com.squareup.okhttp3:okhttp:${DependencyVersions.okhttp}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${DependencyVersions.okhttp}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${DependencyVersions.retrofit}"
        const val coroutinesConverter =
            "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${DependencyVersions.retrofitCoroutinesAdapter}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${DependencyVersions.retrofit}"

        object Moshi {
            const val core = "com.squareup.moshi:moshi:${DependencyVersions.moshi}"
            const val kapt = "com.squareup.moshi:moshi-kotlin-codegen:${DependencyVersions.moshi}"
        }
    }

    object Room {
        const val roomKtx = "androidx.room:room-ktx:${DependencyVersions.room}"
        const val compiler = "androidx.room:room-compiler:${DependencyVersions.room}"
    }

    object Testing {
        const val assertJ = "org.assertj:assertj-core:${DependencyVersions.assertJ}"
        const val junit = "junit:junit:${DependencyVersions.junit4}"
        const val truth = "com.google.truth:truth:${DependencyVersions.truth}"
        const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${DependencyVersions.mockitoKotlin}"
        const val turbine = "app.cash.turbine:turbine:${DependencyVersions.turbine}"
        const val robolectric: String = "org.robolectric:robolectric:${DependencyVersions.robolectric}"

        object AndroidX {
            const val junit = "androidx.test.ext:junit:${DependencyVersions.junitTestExt}"
            const val junitKtx = "androidx.test.ext:junit-ktx:${DependencyVersions.junitTestExt}"
            const val core = "androidx.arch.core:core-testing:${DependencyVersions.archCoreTesting}"
            const val fragment = "androidx.fragment:fragment-testing:${DependencyVersions.fragmentTesting}"
            const val rules: String = "androidx.test:rules:${DependencyVersions.androidxTest}"
            const val runner: String = "androidx.test:runner:${DependencyVersions.androidxTest}"

            object Compose {
                const val ui = "androidx.compose.ui:ui-test-junit4:${DependencyVersions.compose}"
            }
        }

        object Coroutines {
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependencyVersions.coroutines}"
        }

        object Espresso {
            const val core: String = "androidx.test.espresso:espresso-core:${DependencyVersions.espresso}"
            const val contrib: String = "androidx.test.espresso:espresso-contrib:${DependencyVersions.espresso}"
        }

        object Junit {
            const val api = "org.junit.jupiter:junit-jupiter-api:${DependencyVersions.junit5}"
            const val engine = "org.junit.jupiter:junit-jupiter-engine:${DependencyVersions.junit5}"
            const val params = "org.junit.jupiter:junit-jupiter-params:${DependencyVersions.junit5}"
            const val runner = "org.junit.platform:junit-platform-runner:${DependencyVersions.junitRunner}"

            // TODO:: Remove this dependency after migrating to junit5
            const val vintage = "org.junit.vintage:junit-vintage-engine:${DependencyVersions.junit5}"
        }

        object Hilt {
            const val androidTesting = "com.google.dagger:hilt-android-testing:${DependencyVersions.hilt}"
        }

        object Mockito {
            const val android = "org.mockito:mockito-android:${DependencyVersions.mockito}"
            const val core = "org.mockito:mockito-core:${DependencyVersions.mockito}"
        }
    }
}
