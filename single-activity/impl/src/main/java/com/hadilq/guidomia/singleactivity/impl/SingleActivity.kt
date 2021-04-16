package com.hadilq.guidomia.singleactivity.impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hadilq.guidomia.core.api.ViewBindingProvider
import com.hadilq.guidomia.singleactivity.impl.databinding.ActivityMainBinding
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponent
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponentProvider
import javax.inject.Inject

class SingleActivity : AppCompatActivity() {

  private val component: SingleActivityComponent by lazy {
    (application as SingleActivityComponentProvider)
      .singleActivityComponentProvider
      .activity(this)
      .build()
  }

  @Inject
  lateinit var viewBindingProvider: ViewBindingProvider

  private val binding by viewBindingProvider.viewBinding(this) {
    ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    component.inject(this)
  }
}


