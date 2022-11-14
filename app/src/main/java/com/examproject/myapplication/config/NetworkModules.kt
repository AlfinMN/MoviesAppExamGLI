package com.examproject.myapplication.config

import com.examproject.myapplication.movies.data.MoviesAPI
import dagger.Module
import dagger.Provides

@Module
class NetworkModules {
    @Provides
    fun provideMoviesAPI(): MoviesAPI {
        return Connection.urlCon().create(MoviesAPI::class.java)
    }
}