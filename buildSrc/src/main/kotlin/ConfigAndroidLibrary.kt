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
import com.android.build.gradle.LibraryExtension
import com.android.builder.core.BuilderConstants
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun Project.configureAndroidLibrary() {
  androidLibrary {
    compileSdkVersion(Versions.compileSdk)

    defaultConfig {
      minSdkVersion(Versions.minSdk)
      targetSdkVersion(Versions.targetSdk)
    }

    buildTypes {
      getByName("release") {
        isMinifyEnabled = false
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

    variantFilter {
      if (buildType.name == BuilderConstants.DEBUG) {
        ignore = true
      }
    }

    buildFeatures {
      aidl = false
      dataBinding = false
      viewBinding = false
      shaders = false
      buildConfig = false
      renderScript = false
    }
  }
}

/**
 * Configures the [android][LibraryExtension] extension.
 */
fun Project.androidLibrary(configure: Action<LibraryExtension>): Unit =
  (this as ExtensionAware).extensions.configure("android", configure)

/**
 * Configures the [kotlinOptions][KotlinJvmOptions] extension.
 */
fun LibraryExtension.kotlinOptions(configure: Action<KotlinJvmOptions>): Unit =
  (this as ExtensionAware).extensions.configure("kotlinOptions", configure)


