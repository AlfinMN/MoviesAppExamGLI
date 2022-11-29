package com.examproject.myapplication.movies.data

import com.examproject.myapplication.utils.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET(GENRE_MOVIE)
    fun getGenre(@Query("api_key") api_key: String) : Call<ResGenre>

    @GET(DISCOVER_MOVIE)
    fun getMovieByGenre(@Query("api_key") api_key: String,
                        @Query("with_genres",) with_genres: Int,
                        @Query("page") page: Int) : Call<MoviesModel>

    @GET(DISCOVER_MOVIE)
    fun getPopularMovies(@Query("api_key") api_key: String,
                         @Query("sort_by") sort_by: String) : Call<MoviesModel>

    @GET(DETAIL_MOVIES)
    fun getDetailMovie(@Path("movie_id")movie_id : Int,
                       @Query("api_key") api_key: String,
                       ) :Call<ResMovieDetail>
    @GET(LATEST_MOVIE)
    fun getLatestMovie(@Query("api_key") api_key: String):Call<ResLatest>

    @GET(SEARCH_MOVIE)
    fun getSearchMovie(@Query("api_key") api_key: String,
                        @Query("query")query: String):Call<ResLatest>
}