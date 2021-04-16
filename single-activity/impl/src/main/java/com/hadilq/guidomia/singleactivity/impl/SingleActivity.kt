package com.hadilq.guidomia.singleactivity.impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hadilq.guidomia.core.api.ViewBindingProvider
import com.hadilq.guidomia.core.api.di.DaggerSingleActivityScope
import com.hadilq.guidomia.core.api.di.SingleActivityScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.guidomia.api.GuidomiaNavigatorFactory
import com.hadilq.guidomia.singleactivity.impl.databinding.ActivityMainBinding
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponent
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponentProvider
import javax.inject.Inject

@DaggerSingleActivityScope
@SingleIn(SingleActivityScope::class)
class SingleActivity : AppCompatActivity() {

  private val component: SingleActivityComponent by lazy {
    (application as SingleActivityComponentProvider)
      .singleActivityComponentProvider
      .build()
  }

  @Inject
  internal lateinit var viewBindingProvider: ViewBindingProvider

  @Inject
  internal lateinit var guidomiaNavigatorFactory: GuidomiaNavigatorFactory

  private val binding by viewBindingProvider.viewBinding(this) {
    ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    component.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    if (savedInstanceState == null) {
      guidomiaNavigatorFactory.create(this).commit()
    }
  }
}


