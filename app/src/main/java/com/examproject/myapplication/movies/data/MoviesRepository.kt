package com.examproject.myapplication.movies.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MoviesRepository @Inject constructor(val moviesAPI: MoviesAPI){
    val resGetGenre = MutableLiveData<ResGenre>()
    val resDetailMovie = MutableLiveData<ResMovieDetail>()
    val resMovies = MutableLiveData<MoviesModel>()
    val resVideos = MutableLiveData<MoviesVideo>()
    val resLatest = MutableLiveData<ResLatest>()

    fun getGenre(api_key : String,context: Context){
        moviesAPI.getGenre(api_key).enqueue(object : Callback<ResGenre>{
            override fun onResponse(call: Call<ResGenre>, response: Response<ResGenre>) {
                if (response.isSuccessful){
                    try {
                        val res = response.body()
                        resGetGenre.value = res
                    }catch (e:Exception){
                        Toast.makeText(context,"Fetch Failed: "+e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Request Failed: "+response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResGenre>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed: "+t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getMovieByGenre(api_key: String,resMovie: MutableLiveData<Any>,with_genres:Int,page:Int,context: Context){
        moviesAPI.getMovieByGenre(api_key,with_genres,page).enqueue(object : Callback<MoviesModel>{
            override fun onResponse(call: Call<MoviesModel>, response: Response<MoviesModel>) {
                if (response.isSuccessful){
                    try {
                        resMovie.value = response.body()
                    }catch (e:Exception){
                        Toast.makeText(context,"Fetch Failed: "+e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Request Failed: "+response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MoviesModel>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed: "+t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getSearchMovie(api_key: String, query:String,context: Context){
        val link = moviesAPI.getSearchMovie(api_key, query).request().url.toString()
        Log.e("LINK",link)
        moviesAPI.getSearchMovie(api_key, query).enqueue(object : Callback<ResLatest>{
            override fun onResponse(call: Call<ResLatest>, response: Response<ResLatest>) {
                if (response.isSuccessful){
                    try {
                        resLatest.value = response.body()
                    }catch (e:Exception){
                        Toast.makeText(context,"Fetch Failed: "+e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Request Failed: "+response.code(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResLatest>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed: "+t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getPopularMovies(api_key: String,context: Context){
        moviesAPI.getPopularMovies(api_key, sort_by = "popularity.desc").enqueue(object : Callback<MoviesModel> {
            override fun onResponse(call: Call<MoviesModel>, response: Response<MoviesModel>) {
                if (response.isSuccessful){
                    try {
                        resMovies.value = response.body()
                    }catch (e:Exception){
                        Toast.makeText(context,"Fetch Failed: "+e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Request Failed: "+response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MoviesModel>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed: "+t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getDetailMovie(api_key: String,movie_id:Int,context: Context){
        moviesAPI.getDetailMovie(movie_id,api_key).enqueue(object : Callback<ResMovieDetail>{
            override fun onResponse(
                call: Call<ResMovieDetail>,
                response: Response<ResMovieDetail>
            ) {
                if (response.isSuccessful){
                    try {
                        resDetailMovie.value = response.body()
                    }catch (e:Exception){
                        Toast.makeText(context,"Fetch Failed: "+e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Request Failed: "+response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResMovieDetail>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed: "+t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getLatestMovie(api_key: String,context: Context){
        moviesAPI.getLatestMovie(api_key).enqueue(object : Callback<ResLatest>{
            override fun onResponse(call: Call<ResLatest>, response: Response<ResLatest>) {
                if (response.isSuccessful){
                    try {
                       resLatest.value = response.body()
                    }catch (e:Exception){
                        Toast.makeText(context,"Fetch Failed: "+e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Request Failed: "+response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResLatest>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed: "+t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getVideos(api_key: String,movie_id: Int,context: Context){
        moviesAPI.getVideos(movie_id, api_key).enqueue(object :Callback<ResVideos>{
            override fun onResponse(call: Call<ResVideos>, response: Response<ResVideos>) {
                if (response.isSuccessful){
                    try {
                        resVideos.value = response.body()!!.results[0]
                    }catch (e:Exception){
                        Toast.makeText(context,"Fetch Failed: "+e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Request Failed: "+response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResVideos>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed: "+t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

}