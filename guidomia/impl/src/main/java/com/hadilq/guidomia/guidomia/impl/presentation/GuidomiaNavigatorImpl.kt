package com.hadilq.guidomia.guidomia.impl.presentation

import androidx.appcompat.app.AppCompatActivity
import com.hadilq.guidomia.core.api.FragmentFactory
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.guidomia.api.GuidomiaNavigator
import com.hadilq.guidomia.guidomia.api.GuidomiaNavigatorFactory
import com.hadilq.guidomia.singleactivity.api.NavigatorFactory
import com.squareup.anvil.annotations.ContributesBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@ContributesBinding(AppScope::class)
@AssistedFactory
interface GuidomiaNavigatorFactoryImpl : GuidomiaNavigatorFactory {

  override fun create(activity: AppCompatActivity): GuidomiaNavigatorImpl
}

class GuidomiaNavigatorImpl @AssistedInject constructor(
  private val navigatorFactory: NavigatorFactory,
  private val fragmentFactory: FragmentFactory,
  @Assisted private val activity: AppCompatActivity
) : GuidomiaNavigator {

  override fun commit() {
    navigatorFactory.create(activity)
      .commit(fragmentFactory.instantiate(GuidomiaFragment::class))
  }
}
