package com.hadilq.guidomia.guidomia.impl.presentation.di

import com.hadilq.guidomia.di.api.RetainScope
import com.hadilq.guidomia.di.api.SingleIn
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaViewModel
import com.squareup.anvil.annotations.MergeSubcomponent

@SingleIn(RetainScope::class)
@MergeSubcomponent(RetainScope::class)
interface GuidomiaRetainComponent {

  fun guidomiaViewModel(): GuidomiaViewModel
}
