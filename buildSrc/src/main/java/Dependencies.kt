object Versions {
    const val gradle = "3.1.4"
    const val kotlin = "1.2.61"

    // Support Lib
    const val support = "27.1.1"
    const val constraintLayout = "1.1.2"

    //Android Achitecture Components
    const val androidArch = "1.1.0"

    //Network
    const val retrofit2 = "2.4.0"
    const val okHttp3 = "3.9.1"

    const val dagger2 = "2.17"

    //Testing
    const val junit4 = "4.12"
    const val androidTestRunner = "1.2.0"
    const val androidEspresso = "3.2.0"

    const val rxJava    = "2.2.0"
    const val rxAndroid = "2.1.0"
    const val rxKotlin  = "2.3.0"

    const val timber = "4.7.1"

    const val glide = "4.8.0"

}

object Dependencies {

    // Plugins
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradle}"

    //Kotlin
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    //Android Support [https://developer.android.com/topic/libraries/support-library/packages]
    const val supportCompatV7 = "com.android.support:appcompat-v7:${Versions.support}"
    const val supportConstraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
    const val supportRecyclerView = "com.android.support:recyclerview-v7:${Versions.support}"


    //Android Architecture Components
    const val archLifeCycleExts = "android.arch.lifecycle:extensions:${Versions.androidArch}"
    const val archLifeCycleJava8 = "android.arch.lifecycle:common-java8:${Versions.androidArch}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object NetworkDependencies {
    const val retrofit2         = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2_gson    = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val retrofit2_rx      = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2}"
    const val okHttp3_logging   = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp3}"
}

object RxDependencies {
    const val rxJava    = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxKotlin  = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
}

object InjectionDependencies {
    const val dagger2_android       = "com.google.dagger:dagger-android:${Versions.dagger2}"
    const val dagger2_androidSupport= "com.google.dagger:dagger-android-support:${Versions.dagger2}"
    const val dagger2_compiler      = "com.google.dagger:dagger-compiler:${Versions.dagger2}"
    const val dagger2_annotation    = "com.google.dagger:dagger-android-processor:${Versions.dagger2}"
}

object TestingDependencies {
    const val junit4 = "junit:junit:4.12"
    const val testRunner = "com.android.support.test:runner:1.0.2"
    const val espresso = "com.android.support.test.espresso:espresso-core:3.0.2"
}