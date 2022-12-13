package com.examproject.myapplication.config

import com.examproject.myapplication.movies.data.MoviesAPI
import dagger.Module
import dagger.Provides

/**
 * Created by Alfin Muhammad Nurdin on 12/12/22.
 * alfinmuhammadnurdin@gmail.com
 */

@Module
class NetworkModules {
    @Provides
    fun provideMoviesAPI(): MoviesAPI {
        return Connection.urlCon().create(MoviesAPI::class.java)
    }
}