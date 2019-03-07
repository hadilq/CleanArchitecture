plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    defaultConfig {
        applicationId = "com.gitlab.sample.cleanarchitecture"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = Versions.versionCode
        versionName = Versions.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        setTargetCompatibility(1.8)
        setSourceCompatibility(1.8)
    }
}

dependencies {
    kapt(Depends.daggerCompiler)
    kapt(Depends.daggerProcessor)
    kapt(Depends.archComponentsCompiler)

    implementation(Depends.kotlin)
    implementation(Depends.supportCompat)
    implementation(Depends.recycler)
    implementation(Depends.paging)
    implementation(Depends.pagingRx)
    implementation(Depends.extensions)
    implementation(Depends.picasso)
    implementation(Depends.dagger)
    implementation(Depends.daggerAndroid)
    implementation(Depends.archComponents)
    implementation(Depends.rxJava)
    implementation(Depends.retrofit)
    implementation(Depends.navigationFragment)
    implementation(Depends.navigationUI)

    testImplementation(Depends.kotlin)
    testImplementation(Depends.kotlinTest)
    testImplementation(Depends.robolectric)
    testImplementation(Depends.junit)
    testImplementation(Depends.mockito)

    androidTestImplementation(Depends.testRunner)
    androidTestImplementation(Depends.testRules)
    androidTestImplementation(Depends.espressoCore)
    androidTestImplementation(Depends.espressoIntents)
    androidTestImplementation(Depends.androidAnnotations)

    implementation(project(":common-presentation"))
    implementation(project(":common-data"))
    implementation(project(":common-domain"))
    implementation(project(":albums-presentation"))
    implementation(project(":albums-data"))
    implementation(project(":albums-domain"))
    implementation(project(":album-details-presentation"))
    implementation(project(":album-details-data"))
    implementation(project(":album-details-domain"))
}
