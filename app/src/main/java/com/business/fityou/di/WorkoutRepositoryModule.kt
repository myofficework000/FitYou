package com.business.fityou.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.business.fityou.data.repository.WorkoutRepositoryImpl
import com.business.fityou.domain.WorkoutRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkoutRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository (workoutRepositoryImpl: WorkoutRepositoryImpl): WorkoutRepository
}