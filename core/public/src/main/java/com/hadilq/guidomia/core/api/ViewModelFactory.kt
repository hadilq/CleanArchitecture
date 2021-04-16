package com.hadilq.guidomia.core.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface ViewModelFactory : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T
}
