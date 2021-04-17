package com.hadilq.guidomia.guidomia.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.guidomia.core.api.di.RetainScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.guidomia.impl.domain.usecase.GetCars
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@SingleIn(RetainScope::class)
class GuidomiaViewModel @Inject constructor(
  private val getCars: GetCars,
  private val carMapper: CarModelMapper,
) : ViewModel(), CarItemOnClick {

  private val _uiState = MutableStateFlow(CarListUiState.Success(emptyList()))
  val uiState: StateFlow<CarListUiState> = _uiState

  init {
    viewModelScope.launch {
      getCars().collect { cars ->
        val list = cars.flatMapIndexed { index, car ->
          val collapsed = index != 0
          listOf(carMapper.map(car, collapsed), LineModel)
        }.toMutableList()
        list.removeAt(list.size - 1)
        _uiState.value = CarListUiState.Success(list)
      }
    }
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
}

sealed class CarListUiState {
  data class Success(val list: List<CarListModel>) : CarListUiState()
  data class Error(val exception: Throwable) : CarListUiState()
}
