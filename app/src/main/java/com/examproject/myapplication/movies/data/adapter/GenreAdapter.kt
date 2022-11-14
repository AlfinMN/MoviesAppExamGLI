package com.examproject.myapplication.movies.data.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examproject.myapplication.databinding.ItemGenreBinding
import com.examproject.myapplication.movies.data.Genre
import com.examproject.myapplication.movies.view.MovieByGenreActivity

/**
 * Created by Alfin Muhammad Nurdin on 14/11/22.
 * alfinmuhammadnurdin@gmail.com
 */

class GenreAdapter(val listData : ArrayList<Genre>,val activity: Activity) :RecyclerView.Adapter<GenreAdapter.GenreVH>() {

    inner class GenreVH(private val binding : ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun data(dataGenre : Genre){
            binding.nameGenre.text = dataGenre.name
            itemView.setOnClickListener {
                val toMovieByGenre= Intent(activity, MovieByGenreActivity::class.java)
                toMovieByGenre.putExtra(MovieByGenreActivity.EXTRA_ID,dataGenre.id)
                toMovieByGenre.putExtra(MovieByGenreActivity.EXTRA_NAME,dataGenre.name)
                activity.startActivity(toMovieByGenre)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreVH {
        val view = ItemGenreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GenreVH((view))
    }

    override fun onBindViewHolder(holder: GenreVH, position: Int) {
        holder.data(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}


