package com.hadilq.guidomia.guidomia.api

import androidx.appcompat.app.AppCompatActivity

interface GuidomiaNavigatorFactory {

  fun create(activity: AppCompatActivity): GuidomiaNavigator
}
