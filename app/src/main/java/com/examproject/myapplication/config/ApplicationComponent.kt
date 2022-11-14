package com.examproject.myapplication.config


import com.examproject.myapplication.home.view.HomeActivity
import com.examproject.myapplication.movies.view.DetailMovieActivity
import com.examproject.myapplication.movies.view.MovieByGenreActivity
import dagger.Component

@Component(modules = [NetworkModules::class])
interface ApplicationComponent {
fun inject(homeActivity: HomeActivity)
fun inject(movieByGenreActivity: MovieByGenreActivity)
fun inject(detailMovieActivity: DetailMovieActivity)
}