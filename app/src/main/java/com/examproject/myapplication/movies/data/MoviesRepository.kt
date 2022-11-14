package com.examproject.myapplication.movies.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(val moviesAPI: MoviesAPI){
    val resGetGenre = MutableLiveData<ResGenre>()
    val resDetailMovie = MutableLiveData<ResMovieDetail>()

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
}