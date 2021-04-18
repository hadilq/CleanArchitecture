package com.hadilq.guidomia.database.impl.di

import android.content.Context
import androidx.room.Room
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.database.impl.AppDatabase
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@[Module ContributesTo(AppScope::class)]
object DatabaseModule {

  @[Provides SingleIn(AppScope::class)]
  fun provideDatabase(context: Context) = Room.databaseBuilder(
    context.applicationContext,
    AppDatabase::class.java, "car-database"
  ).build()

  @[Provides SingleIn(AppScope::class)]
  fun provideCarDao(database: AppDatabase) = database.carDao()
}
