package com.examproject.myapplication.movies.data

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.examproject.myapplication.utils.BASE_URL_IMG
import com.google.gson.annotations.SerializedName

data class MoviesModel (
    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<MovieList>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
        ){}


data class MovieList(@field:SerializedName("overview")
                     val overview: String? = null,

                     @field:SerializedName("original_language")
                     val originalLanguage: String? = null,

                     @field:SerializedName("original_title")
                     val originalTitle: String? = null,

                     @field:SerializedName("video")
                     val video: Boolean? = null,

                     @field:SerializedName("title")
                     val title: String? = null,

                     @field:SerializedName("genre_ids")
                     val genreIds: List<Int?>? = null,

                     @field:SerializedName("poster_path")
                     val posterPath: String? = null,

                     @field:SerializedName("backdrop_path")
                     val backdropPath: String? = null,

                     @field:SerializedName("release_date")
                     val releaseDate: String? = null,

                     @field:SerializedName("popularity")
                     val popularity: Double? = null,

                     @field:SerializedName("vote_average")
                     val voteAverage: Double? = null,

                     @field:SerializedName("id")
                     val id: Int? = null,

                     @field:SerializedName("adult")
                     val adult: Boolean? = null,

                     @field:SerializedName("vote_count")
                     val voteCount: Int? = null){

}

@BindingAdapter("app:imageMovie" , "app:progressMovie")
fun setImageMovie(image: ImageView, imageUrl: String?, progressMovie : ProgressBar) {
    val url = BASE_URL_IMG + imageUrl
    Glide.with(image.context)
        .load(url)
        .listener(object: RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressMovie.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressMovie.visibility = View.GONE
                return false
            }

        })
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .into(image)

}

class ResGenre(
    val genres : ArrayList<Genre>
){}

class Genre(
    val id : Int,
    val name : String
){}

class ResMovieDetail(
    val adult: Boolean,
    val backdrop_path: String,
    val budget : Int,
    val genres: ArrayList<Genre>,
    val id: Int,
    val original_title : String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val production_companies : ArrayList<ProductCompanies>,
    val production_countries : ArrayList<Countries>,
    val release_date : String,
    val runtime : Int ,
    val vote_average : Float
){}

class ProductCompanies(
    val id: Int,
    val logo_path : String,
    val name : String,

){}

class Countries (
    val name: String
        ){}

class ResLatest(
    val page : Int,
    val results: List<ResMovieDetail>,
    val total_results : Int
){}

class ResVideos(
    val id: Int,
    val results: ArrayList<MoviesVideo>
){}

class MoviesVideo(
    val name: String,
    val key : String,
    val site : String,
    val size : Int,
    val type : String ,
){}

class ResReview(
    val id: Int,
    val results: ArrayList<MoviesReview>
){}

class MoviesReview(
    val author : String ,
    val author_details : AuthorDetails,
    val content : String,
){}

class AuthorDetails(
    val name: String,
    val username : String,
    val avatar_path : String ,
    val rating : Float
){}
