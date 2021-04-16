package com.hadilq.guidomia.guidomia.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.guidomia.guidomia.impl.domain.usecase.LoadCars
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class GuidomiaViewModel @Inject constructor(
  private val loadCars: LoadCars,
  private val carMapper: CarModelMapper,
) : ViewModel() {

  private val _uiState = MutableStateFlow(CarListUiState.Success(emptyList()))
  val uiState: StateFlow<CarListUiState> = _uiState

  init {
    viewModelScope.launch {
      loadCars().collect { cars ->
        _uiState.value = CarListUiState.Success(cars.map { carMapper.map(it) })
      }
    }
  }
}

sealed class CarListUiState {
  data class Success(val list: List<CarListModel>) : CarListUiState()
  data class Error(val exception: Throwable) : CarListUiState()
}
