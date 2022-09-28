package com.omarelrayes.moviedb.core.di

import com.omarelrayes.moviedb.features.list.data.MoviesRepositoryImpl
import com.omarelrayes.moviedb.features.list.domain.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    @ViewModelScoped
    fun provideMoviesRepository(
        moviesRepository: MoviesRepositoryImpl
    ): MoviesRepository
}
