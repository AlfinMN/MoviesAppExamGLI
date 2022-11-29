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
import com.examproject.myapplication.databinding.ItemMovieBinding
import com.examproject.myapplication.databinding.ItemPosterBinding
import com.examproject.myapplication.databinding.ItemSearchMovieBinding
import com.examproject.myapplication.movies.data.ResMovieDetail
import com.examproject.myapplication.movies.view.DetailMovieActivity
import com.examproject.myapplication.utils.BASE_URL_IMG


/**
 * Created by Alfin Muhammad Nurdin on 29/11/22.
 * alfinmuhammadnurdin@gmail.com
 */
class AdapterSearchMovie(val listData : List<ResMovieDetail>,val activity: Activity):RecyclerView.Adapter<AdapterSearchMovie.SearchVH>() {

    inner class SearchVH (private val binding : ItemSearchMovieBinding):RecyclerView.ViewHolder(binding.root){
        fun data(dataMovie : ResMovieDetail){
            binding.movieTitle.text = dataMovie.original_title
            binding.movieYear.text = dataMovie.release_date
            binding.movieDesc.text = dataMovie.overview
            val url = BASE_URL_IMG + dataMovie.poster_path

            Glide.with(activity)
                .load(url)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.movieProgress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.movieProgress.visibility = View.GONE
                        return false
                    }

                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.moviePoster)
            itemView.setOnClickListener {
                val toDetail= Intent(activity, DetailMovieActivity::class.java)
                toDetail.putExtra(DetailMovieActivity.EXTRA_ID,dataMovie.id)
                activity.startActivity(toDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        val view = ItemSearchMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchVH((view))
    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        holder.data(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}