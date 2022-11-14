package com.examproject.myapplication.movies.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.examproject.myapplication.utils.API_KEY
import javax.inject.Inject

class MoviesViewModel @Inject constructor( val moviesRepository: MoviesRepository) {
    val resGetGenre = moviesRepository.resGetGenre
    val resMovieDetail = moviesRepository.resDetailMovie
    private var _topMoviesFirstPageResponse = MutableLiveData<Any>()
    private var _topMoviesNextPageResponse = MutableLiveData<Any>()

    fun requestFirstPageTopMovies(with_genres : Int,page : Int,context: Context) {
        moviesRepository.getMovieByGenre(API_KEY,_topMoviesFirstPageResponse ,with_genres, page,context)
    }

    fun requestFirstNextPageMovies(with_genres : Int,page : Int,context: Context) {
        moviesRepository.getMovieByGenre(API_KEY,_topMoviesNextPageResponse , with_genres, page,context)
    }

    val topMoviesFirstPageResponse : LiveData<Any>
        get() = _topMoviesFirstPageResponse
    val topMoviesNextPageResponse : LiveData<Any>
        get() = _topMoviesNextPageResponse

    fun getGenre(context: Context){
        moviesRepository.getGenre(API_KEY, context)
    }

    fun getDetailMovie(movie_id :Int,context: Context){
        moviesRepository.getDetailMovie(API_KEY, movie_id, context)
    }

}