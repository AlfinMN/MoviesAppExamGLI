package com.examproject.myapplication.movies.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examproject.myapplication.databinding.ItemPosterBinding
import com.examproject.myapplication.databinding.ItemReviewsBinding
import com.examproject.myapplication.movies.data.MoviesReview
import com.examproject.myapplication.movies.data.ResReview


/**
 * Created by Alfin Muhammad Nurdin on 13/12/22.
 * alfinmuhammadnurdin@gmail.com
 */
class AdapterReview (val listData: List<MoviesReview>):RecyclerView.Adapter<AdapterReview.ReviewVH>(){

    inner class ReviewVH(private val binding : ItemReviewsBinding):RecyclerView.ViewHolder(binding.root){
        fun data(dataReview:MoviesReview){
            binding.apply {
                author.text = dataReview.author
                content.text = dataReview.content
                val starRate = dataReview.author_details.rating.toInt() / 2
                star.rating = starRate.toFloat()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewVH {
        val view = ItemReviewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewVH((view))
    }

    override fun onBindViewHolder(holder: ReviewVH, position: Int) {
       holder.data(listData[position])
    }

    override fun getItemCount(): Int {
       return listData.size
    }
}


