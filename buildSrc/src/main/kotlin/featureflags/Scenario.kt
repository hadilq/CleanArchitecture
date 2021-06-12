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

val scenarios = mapOf(
  Scenario.default to listOf(Scenario.guidomiaWithDatabase),
  Scenario.root to listOf(),
  Scenario.guidomia to listOf(Modules.guidomiaImpl),
  Scenario.guidomiaWithDatabase to listOf(Modules.databaseImpl)
)

private object Scenario {
  const val default = DEFAULT_SCENARIO
  const val root = ">"
  const val guidomia = ">guidomia"
  const val guidomiaWithDatabase = ">guidomia>database"
}
