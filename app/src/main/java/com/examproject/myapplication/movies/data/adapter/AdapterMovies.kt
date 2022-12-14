package com.examproject.myapplication.movies.data.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.examproject.myapplication.BR
import com.examproject.myapplication.R
import com.examproject.myapplication.databinding.ItemLoadingBinding
import com.examproject.myapplication.databinding.ItemMovieBinding
import com.examproject.myapplication.movies.data.MovieList
import com.examproject.myapplication.movies.view.DetailMovieActivity
import com.examproject.myapplication.movies.view.MovieByGenreActivity
import com.examproject.myapplication.utils.PaginationAdapterCallback


/**
 * Created by Alfin Muhammad Nurdin on 12/12/22.
 * alfinmuhammadnurdin@gmail.com
 */

class AdapterMovies(private var mActivity: MovieByGenreActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    PaginationAdapterCallback {

    private val item : Int = 0
    private val loading : Int = 1

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false

    private var errorMsg: String? = ""

    private var moviesModels: MutableList<MovieList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  if(viewType == item){
            val binding: ItemMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false)
            MoviesVH(binding)
        }else{
            val binding: ItemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_loading, parent, false)
            LoadingVH(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = moviesModels[position]
        if(getItemViewType(position) == item){
            val moviesVH: MoviesVH = holder as MoviesVH
            moviesVH.itemRowBinding.movieProgress.visibility = View.VISIBLE
            moviesVH.bind(model)
            moviesVH.itemView.setOnClickListener {
                val toDetail= Intent(mActivity, DetailMovieActivity::class.java)
                toDetail.putExtra(DetailMovieActivity.EXTRA_ID,model.id)
                mActivity.startActivity(toDetail)
            }
        }else{
            val loadingVH: LoadingVH = holder as LoadingVH
            if (retryPageLoad) {
                loadingVH.itemRowBinding.loadmoreErrorlayout.visibility = View.VISIBLE
                loadingVH.itemRowBinding.loadmoreProgress.visibility = View.GONE

                if(errorMsg != null) loadingVH.itemRowBinding.loadmoreErrortxt.text = errorMsg
                else loadingVH.itemRowBinding.loadmoreErrortxt.text = mActivity.getString(R.string.error_msg_unknown)

            } else {
                loadingVH.itemRowBinding.loadmoreErrorlayout.visibility = View.GONE
                loadingVH.itemRowBinding.loadmoreProgress.visibility = View.VISIBLE
            }

            loadingVH.itemRowBinding.loadmoreRetry.setOnClickListener{
                showRetry(false, "")
                retryPageLoad()
            }
            loadingVH.itemRowBinding.loadmoreErrorlayout.setOnClickListener{
                showRetry(false, "")
                retryPageLoad()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (moviesModels.size > 0) moviesModels.size else 0
    }
    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            item
        }else {
            if (position == moviesModels.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }
    override fun retryPageLoad() {
        mActivity.loadNextPage()
    }

    fun showRetry(show: Boolean, errorMsg: String) {
        retryPageLoad = show
        notifyItemChanged(moviesModels.size - 1)
        this.errorMsg = errorMsg
    }
    fun add(movie: MovieList) {
        moviesModels.add(movie)
        notifyItemInserted(moviesModels.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(MovieList())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position: Int =moviesModels.size -1
        val movie: MovieList = moviesModels[position]

        if(movie != null){
            moviesModels.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    fun addAll(movies: MutableList<MovieList>) {
        for(movie in movies){
            add(movie)
        }
    }
}

class LoadingVH(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    var itemRowBinding: ItemLoadingBinding = binding
}

class MoviesVH(binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    var itemRowBinding: ItemMovieBinding = binding
    fun bind(obj: Any?) {
        itemRowBinding.setVariable(BR.model,obj)
        itemRowBinding.executePendingBindings()
    }




}
