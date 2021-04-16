package com.hadilq.guidomia.singleactivity.impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.singleactivity.api.Navigator
import com.hadilq.guidomia.singleactivity.api.NavigatorFactory
import com.squareup.anvil.annotations.ContributesBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.*

@ContributesBinding(AppScope::class)
@AssistedFactory
interface NavigatorFactoryImpl : NavigatorFactory {

  override fun create(activity: AppCompatActivity): NavigatorImpl
}

class NavigatorImpl @AssistedInject constructor(
  @Assisted private val activity: AppCompatActivity
) : Navigator, LifecycleObserver {

  private var paused = true
  private var savedState = false
  private var fragmentsCache: Queue<Fragment> = ArrayDeque()

  init {
    activity.lifecycle.addObserver(this)
    activity.savedStateRegistry.registerSavedStateProvider(SAVED_STATE_KEY) {
      savedState = true
      paused = true
      fragmentsCache.clear()
      Bundle()
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  fun onResume() {
    paused = false
    repeat(fragmentsCache.size) {
      val fragment = fragmentsCache.poll()
      if (fragment != null) {
        commit(fragment)
      }
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  fun onPause() {
    paused = true
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy() {
    fragmentsCache.clear()
  }

  override fun commit(fragment: Fragment) {
    if (paused) {
      if (savedState) {
        fragmentsCache.clear()
      } else {
        fragmentsCache.offer(fragment)
      }
    } else {
      activity.supportFragmentManager.commit {
        setReorderingAllowed(true)
        replace(R.id.fragment_content_main, fragment, fragment.tag)
      }
    }
  }
}

private const val SAVED_STATE_KEY = "SAVED_STATE_KEY"
