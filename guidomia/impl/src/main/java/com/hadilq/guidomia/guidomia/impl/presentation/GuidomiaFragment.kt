package com.hadilq.guidomia.guidomia.impl.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hadilq.guidomia.core.api.ViewModelFactory
import javax.inject.Inject

class GuidomiaFragment @Inject constructor() : Fragment() {

  @Inject
  internal lateinit var viewModelFactory: ViewModelFactory

  private val viewModel: GuidomiaViewModel by lazy {
    ViewModelProvider(this, viewModelFactory)
      .get(GuidomiaViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }
}
