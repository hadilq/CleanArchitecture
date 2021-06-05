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
package com.hadilq.guidomia.guidomia.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.guidomia.di.api.RetainScope
import com.hadilq.guidomia.di.api.SingleIn
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import com.hadilq.guidomia.guidomia.impl.domain.usecase.GetCars
import com.hadilq.guidomia.guidomia.impl.domain.usecase.GetFilteredCars
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@SingleIn(RetainScope::class)
class GuidomiaViewModel @Inject constructor(
  private val getCars: GetCars,
  private val getFilteredCars: GetFilteredCars,
  private val carMapper: CarModelMapper,
) : ViewModel(), CarItemOnClick, CarItemFilter {

  private val _uiState = MutableStateFlow(CarListUiState.Success(emptyList()))
  val uiState: StateFlow<CarListUiState> = _uiState

  init {
    viewModelScope.launch {
      getCars().collect(::updateCars)
    }
  }

  private fun updateCars(cars: List<CarEntity>, filter: FilterModel = FilterModel()) {
    val list = cars.flatMapIndexed { index, car ->
      val collapsed = index != 0
      listOf(carMapper.map(car, collapsed), LineModel)
    }.toMutableList()
    if (list.size > 0) {
      list.removeAt(list.size - 1)
    }
    list.add(0, filter)
    _uiState.value = CarListUiState.Success(list)
  }

  override fun onClickCar(position: Int) {
    viewModelScope.launch {
      _uiState.value = CarListUiState.Success(_uiState.value.list.mapIndexed { index, carList ->
        if (carList is CarModel) {
          if (index == position) {
            carList.copy(collapsed = false)
          } else {
            carList.copy(collapsed = true)
          }
        } else {
          carList
        }
      })
    }
  }

  override fun onNewFilter(filterModel: FilterModel) {
    viewModelScope.launch {
      getFilteredCars(carMapper.map(filterModel)).collect {
        updateCars(it, filterModel)
      }
    }
  }
}

sealed class CarListUiState {
  data class Success(val list: List<CarListModel>) : CarListUiState()
  data class Error(val exception: Throwable) : CarListUiState()
}
