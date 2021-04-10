package dependencies

object BuildVersions {
    val minSdkVersion = 21
    val targetSdkVersion = 30
    val compileSdkVersion = 30
    val buildToolsVersion = "30.0.3"
    val versionMajor = 3
    val versionMinor = 0
    val versionPatch = 0
    val versionBuild = 0 // Bump for dogfood builds, public betas, etc.
    val versionCode  = versionMajor * 10000 + versionMinor * 1000 + versionPatch * 100 + versionBuild
    val versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
}

object PluginsVersions {
    const val androidGradle = "7.0.0-alpha08"
    const val kotlin = "1.4.31"
    const val navigation = "2.3.2"
    const val daggerHiltAndroid: String = "2.33-beta"
    const val spotless = "5.10.2"
    const val ktlint = "0.40.0"
    const val detekt = "1.0.1"
    const val androidJunit5 = "1.7.1.1"
}

object DependencyVersions {
    const val accompanist = "0.6.2"
    const val accompanistGoogle = "0.7.1-SNAPSHOT"
    const val androidxTest = "1.4.0-alpha04"
    const val archCoreTesting = "2.1.0"
    const val appCompat = "1.3.0-beta01"
    const val assertJ = "3.19.0"
    const val compose = "1.0.0-beta03"
    const val composeActivity = "1.3.0-alpha05"
    const val composeViewModel = "1.0.0-alpha03"
    const val constraintLayout = "2.1.0-alpha2"
    const val coroutines = "1.4.3"
    const val coreKtx = "1.6.0-alpha01"
    const val dataStore = "1.0.0-alpha07"
    const val espresso: String = "3.4.0-alpha04"
    const val fragmentTesting: String = "1.3.2"
    const val hilt = "2.33-beta"
    const val hiltViewModel = "1.0.0-alpha03"
    const val jacoco = "0.16.0"
    const val junit4: String = "4.13.2"
    const val junitTestExt = "1.1.3-alpha05"
    const val junit5: String = "5.8.0-M1"
    const val junitRunner: String = "1.3.2"
    const val ktLint: String = "0.41.0"
    const val leakCanary = "2.6"
    const val lifecycle = "2.4.0-alpha01"
    const val material = "1.4.0-alpha01"
    const val moshi = "1.11.0"
    const val mockito = "3.8.0"
    const val mockitoKotlin = "2.2.0"
    const val navigation = "2.3.4"
    const val okhttp = "5.0.0-alpha.2"
    const val paging = "3.0.0-beta03"
    const val pagingCompose = "1.0.0-alpha08"
    const val preferences = "1.1.1"
    const val retrofit = "2.9.0"
    const val retrofitCoroutinesAdapter = "0.9.2"
    const val robolectric = "4.5.1"
    const val room = "2.3.0-beta02"
    const val timber = "4.7.1"
    const val truth = "1.1.2"
    const val turbine = "0.4.1"
}
