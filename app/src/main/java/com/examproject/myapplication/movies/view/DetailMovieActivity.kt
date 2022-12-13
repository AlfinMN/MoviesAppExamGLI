package com.examproject.myapplication.movies.view

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.examproject.myapplication.R
import com.examproject.myapplication.config.MoviesApp
import com.examproject.myapplication.databinding.ActivityDetailMovieBinding
import com.examproject.myapplication.databinding.ActivityMovieByGenreBinding
import com.examproject.myapplication.movies.data.MoviesViewModel
import com.examproject.myapplication.utils.BASE_URL_IMG
import com.google.android.youtube.player.YouTubeBaseActivity
import javax.inject.Inject

class DetailMovieActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDetailMovieBinding
    private var idMovies : Int = 0
    @Inject
    lateinit var moviesViewModel: MoviesViewModel

    var keyMovie =""
    var titleMovie =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (this.applicationContext as MoviesApp).applicationComponent.inject(this)

        idMovies = intent.getIntExtra(EXTRA_ID,0)

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            moviesViewModel.resMovieDetail.observe(this@DetailMovieActivity  , Observer {
                if (it!=null){
                    val url = BASE_URL_IMG+it.backdrop_path
                    Glide.with(this@DetailMovieActivity)
                        .load(url)
                        .listener(object: RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                               movieProgress.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                movieProgress.visibility = View.GONE
                                return false
                            }

                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(imgMovie)
                    val urlImg = BASE_URL_IMG+it.poster_path
                    Glide.with(this@DetailMovieActivity)
                        .load(urlImg)
                        .listener(object: RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                movieProgress.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                movieProgress.visibility = View.GONE
                                return false
                            }

                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(imgMovieTitle)

                    tvTitle.text = it.original_title
                    tvDuration.text ="${it.runtime} min"
                    tvOverview.text =it.overview
                }
            })
            moviesViewModel.getDetailMovie(idMovies,this@DetailMovieActivity)

            moviesViewModel.resVideos.observe(this@DetailMovieActivity, Observer {
                keyMovie = it.key
                titleMovie = it.name
            })
            moviesViewModel.getVideos(idMovies,this@DetailMovieActivity)
            vTrailer.setOnClickListener {
                val toVideos= Intent(this@DetailMovieActivity, PlayVideoActivity::class.java)
                toVideos.putExtra(PlayVideoActivity.EXTRA_KEY,keyMovie)
                toVideos.putExtra(PlayVideoActivity.EXTRA_TITLE,titleMovie)
                this@DetailMovieActivity.startActivity(toVideos)
            }
        }
    }

    companion object{
        const val EXTRA_ID ="idmovies"
    }
}