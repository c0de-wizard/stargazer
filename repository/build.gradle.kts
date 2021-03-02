plugins {
    `android-library-plugin`
}

dependencies {

    implementation(Dependencies.Kotlin.stdlib)

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.retrofitCoroutinesConverter)
    implementation(Dependencies.OkHttp.okhttp)
    implementation(Dependencies.OkHttp.loggingInterceptor)

    implementation(Dependencies.Retrofit.Moshi.core)
    implementation(Dependencies.Retrofit.Moshi.converter)
    kapt(Dependencies.Retrofit.Moshi.kapt)

    implementation(Dependencies.Room.roomKtx)
    kapt(Dependencies.Room.compiler)
}
