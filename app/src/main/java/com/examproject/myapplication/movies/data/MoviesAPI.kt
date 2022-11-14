package com.examproject.myapplication.movies.data

import com.examproject.myapplication.utils.DETAIL_MOVIES
import com.examproject.myapplication.utils.GENRE_MOVIE
import com.examproject.myapplication.utils.MOVIES_BY_GENRE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET(GENRE_MOVIE)
    fun getGenre(@Query("api_key") api_key: String) : Call<ResGenre>

    @GET(MOVIES_BY_GENRE)
    fun getMovieByGenre(@Query("api_key") api_key: String,
                        @Query("with_genres",) with_genres: Int,
                        @Query("page") page: Int) : Call<MoviesModel>

    @GET(DETAIL_MOVIES)
    fun getDetailMovie(@Path("movie_id")movie_id : Int,
                       @Query("api_key") api_key: String,
                       ) :Call<ResMovieDetail>
}