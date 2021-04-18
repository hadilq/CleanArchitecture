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
object Depends {
  const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
  const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
  const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
  const val material = "com.google.android.material:material:${Versions.material}"
  const val constraintLayout =
    "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
  const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
  const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
  const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
  const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
  const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
  const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
  const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
  const val jsonSerialization =
    "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.jsonSerialization}"

  const val junit = "junit:junit:${Versions.junit}"
  const val junitJupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}"
  const val junitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}"
  const val mockk = "io.mockk:mockk:${Versions.mockk}"
  const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtJunit}"
  const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}
