package com.examproject.myapplication.movies.data.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.examproject.myapplication.databinding.ItemGenreBinding
import com.examproject.myapplication.databinding.ItemPosterBinding
import com.examproject.myapplication.movies.data.MovieList
import com.examproject.myapplication.movies.data.ResMovieDetail
import com.examproject.myapplication.movies.view.DetailMovieActivity
import com.examproject.myapplication.utils.BASE_URL_IMG


/**
 * Created by Alfin Muhammad Nurdin on 12/12/22.
 * alfinmuhammadnurdin@gmail.com
 */
class AdapterPosterMovies (val listData : List<ResMovieDetail>, val activity: Activity):RecyclerView.Adapter<AdapterPosterMovies.PosterVH>(){
   inner class PosterVH (private val binding : ItemPosterBinding):RecyclerView.ViewHolder(binding.root){
        fun data(dataPoster:ResMovieDetail){
            binding.title.text = dataPoster.original_title
            val url = BASE_URL_IMG + dataPoster.poster_path
            Glide.with(activity)
                .load(url)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPoster)

            itemView.setOnClickListener {
                val toDetail= Intent(activity, DetailMovieActivity::class.java)
                toDetail.putExtra(DetailMovieActivity.EXTRA_ID,dataPoster.id)
                activity.startActivity(toDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterVH {
        val view = ItemPosterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PosterVH((view))
    }

    override fun onBindViewHolder(holder: PosterVH, position: Int) {
        holder.data(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}