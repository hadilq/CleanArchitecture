package com.hadilq.guidomia.singleactivity.api

import androidx.appcompat.app.AppCompatActivity

interface NavigatorFactory {

  fun create(activity: AppCompatActivity): Navigator
}
