import com.thomaskioko.stargazers.dependencies.Dependencies

plugins {
    `android-library-plugin`
}

dependencies {

    implementation(project(":core"))
    testImplementation(project(":common-testing"))

    implementation(Dependencies.AndroidX.paging)

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.coroutinesConverter)
    implementation(Dependencies.OkHttp.okhttp)
    implementation(Dependencies.OkHttp.loggingInterceptor)

    implementation(Dependencies.Retrofit.Moshi.core)
    implementation(Dependencies.Retrofit.moshiConverter)
    kapt(Dependencies.Retrofit.Moshi.kapt)

    implementation(Dependencies.Room.roomKtx)
    kapt(Dependencies.Room.compiler)
}
