package com.examproject.myapplication.movies.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import com.examproject.myapplication.R
import com.examproject.myapplication.config.MoviesApp
import com.examproject.myapplication.databinding.ActivityMovieByGenreBinding
import com.examproject.myapplication.databinding.ActivitySearchMovieBinding
import com.examproject.myapplication.movies.data.MoviesViewModel
import com.examproject.myapplication.movies.data.adapter.AdapterSearchMovie
import javax.inject.Inject

class SearchMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMovieBinding

    @Inject
    lateinit var moviesViewModel : MoviesViewModel
    lateinit var adapterSearchMovie : AdapterSearchMovie

    var query : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (this.applicationContext as MoviesApp).applicationComponent.inject(this)

         query = intent.getStringExtra(EXTRA_SEARCH).toString()
        observeData()
        binding.apply {
            etSearch.setText(query)
            btnSearch.setOnClickListener {
                searching()
            }
            etSearch.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER ){
                    searching()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun observeData() {
        moviesViewModel.resLatest.observe(this, Observer {
            if (it.total_results!=0){
                showNotfound(false)
                adapterSearchMovie = AdapterSearchMovie(it.results,this)
                binding.recyclerMovies.adapter = adapterSearchMovie
            } else {
                showNotfound(true)
            }
        })
        moviesViewModel.getSearchMovie(query,this)
    }
    private fun searching() {
        query = binding.etSearch.text.toString()
        if (query.isEmpty()) return
       observeData()
    }
    private fun showNotfound(state : Boolean){
        if (state){
            binding.notfound.visibility = View.VISIBLE
            binding.tvNotfound.visibility = View.VISIBLE
        }else{
            binding.notfound.visibility = View.GONE
            binding.tvNotfound.visibility = View.GONE
        }
    }
    companion object{
        const val EXTRA_SEARCH ="searchmovie"
    }
}