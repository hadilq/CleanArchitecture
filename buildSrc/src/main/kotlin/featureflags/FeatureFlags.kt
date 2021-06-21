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
package featureflags

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.add
import java.util.*

fun DependencyHandlerScope.installScenario(
  scenarios: Map<String, List<String>>,
  scenario: String
) {
  if (scenario.isNullOrBlank()) return
  println("Installing scenario: '$scenario'")
  val trimScenario = if (scenario.endsWith(SCENARIO_DELIMITER) && scenario != SCENARIO_DELIMITER) {
    scenario.substringBeforeLast(SCENARIO_DELIMITER)
  } else {
    scenario
  }
  installScenario(scenarios, trimScenario.substringBeforeLast(SCENARIO_DELIMITER))

  scenarios[scenario]?.forEach { dependency ->
    if (dependency.startsWith(SCENARIO_DELIMITER)) {
      installScenario(scenarios, dependency)
    } else {
      implementation(dependency)
    }
  } ?: error("Cannot find the feature flag: '$scenario'")
}

fun Project.loadScenarioFromLocal(): String {
  val props = Properties()
  val inputStream = file("${rootDir}/local.properties").inputStream()

  inputStream.use { props.load(it) }
  return props["scenario"] as? String ?: DEFAULT_SCENARIO
}

/**
 * Returns the runtime Java class of this object.
 */
@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNCHECKED_CAST")
inline val <T : Any> T.javaClass: Class<T>
  @Suppress("UsePropertyAccessSyntax")
  get() = (this as java.lang.Object).getClass() as Class<T>

/**
 * Adds a dependency to the 'implementation' configuration.
 *
 * @param dependencyNotation notation for the dependency to be added.
 * @return The dependency.
 *
 * @see [DependencyHandler.add]
 */
private fun DependencyHandler.implementation(path: Any): Dependency? =
  add("implementation", project(mapOf("path" to path)) as ProjectDependency)

private const val SCENARIO_DELIMITER = ">"
const val DEFAULT_SCENARIO = "${SCENARIO_DELIMITER}default"
