/**
 * Copyright 2021 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import featureflags.installScenario
import featureflags.loadScenarioFromLocal
import featureflags.scenarios


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
    freeCompilerArgs = listOf("-Xinline-classes")
  }

  packagingOptions {
    exclude("META-INF/*.kotlin_module")
  }
}

dependencies {
  installScenario(scenarios, project.loadScenarioFromLocal())

  implementation(project(Modules.corePublic))
  implementation(project(Modules.coreImpl))
  implementation(project(Modules.diPublic))
  implementation(project(Modules.singleActivityPublic))
  implementation(project(Modules.singleActivityImpl))
  implementation(project(Modules.guidomiaPublic))
  implementation(project(Modules.databasePublic))
  implementation(project(Modules.featureFlagsPublic))
  implementation(project(Modules.featureFlagsImpl))

  kapt(Depends.daggerCompiler)

  implementation(Depends.kotlinStdLib)
  implementation(Depends.appCompat)
  implementation(Depends.dagger)
  implementation(Depends.recyclerView)
}
