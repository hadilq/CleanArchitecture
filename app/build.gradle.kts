plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("com.squareup.anvil") version Versions.anvil
}

android {
  compileSdkVersion(Versions.compileSdk)
  defaultConfig {
    applicationId = "com.hadilq.guidomia"
    minSdkVersion(Versions.minSdk)
    targetSdkVersion(Versions.targetSdk)
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("debug") {
      matchingFallbacks.add("release")
    }
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }

  packagingOptions {
    exclude("META-INF/public_release.kotlin_module")
    exclude("META-INF/impl_release.kotlin_module")
  }
}

dependencies {
  implementation(project(Modules.corePublic))
  implementation(project(Modules.coreImpl))
  implementation(project(Modules.singleActivityImpl))
  implementation(project(Modules.guidomiaImpl))

  kapt(Depends.daggerCompiler)

  implementation(Depends.kotlinStdLib)
  implementation(Depends.appCompat)
  implementation(Depends.dagger)
}
