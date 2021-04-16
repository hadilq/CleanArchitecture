package com.hadilq.guidomia.singleactivity.api

import androidx.fragment.app.Fragment

interface Navigator {
  fun commit(fragment: Fragment)
}