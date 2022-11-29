package com.examproject.myapplication.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.examproject.myapplication.R
import com.examproject.myapplication.config.MoviesApp
import com.examproject.myapplication.databinding.ActivityHomeBinding
import com.examproject.myapplication.movies.data.MoviesViewModel
import com.examproject.myapplication.movies.data.adapter.AdapterPosterMovies
import com.examproject.myapplication.movies.data.adapter.GenreAdapter
import com.examproject.myapplication.movies.view.SearchMovieActivity
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    lateinit var genreAdapter: GenreAdapter
    lateinit var adapterPosterMovies: AdapterPosterMovies

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (this.applicationContext as MoviesApp).applicationComponent.inject(this)
        showLoad(true)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.wakanda,"Wakanda Forever", ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.blackadam,"Black Adam", ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.teriffier,"Terrfier 2", ScaleTypes.FIT))

        binding.apply {
            imgslider.setImageList(imageList)
            rvGenre.layoutManager =  StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL)
            moviesViewModel.resGetGenre.observe(this@HomeActivity, Observer {
                if (it!=null){
                    showLoad(false)
                    genreAdapter = GenreAdapter(it.genres,this@HomeActivity)
                    rvGenre.adapter = genreAdapter
                }
            })
            moviesViewModel.getGenre(this@HomeActivity)
            rvLatest.layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.HORIZONTAL,false)
            moviesViewModel.resLatest.observe(this@HomeActivity, Observer {
                if (it!=null){
                    adapterPosterMovies = AdapterPosterMovies(it.results,this@HomeActivity)
                    rvLatest.adapter = adapterPosterMovies
                }
            })
            moviesViewModel.getLatestMovie(this@HomeActivity)
            btnSearch.setOnClickListener {
                saerching()
            }
            etSearch.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER ){
                    saerching()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }
    private fun showLoad(state : Boolean){
        if (state){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }
    private fun saerching() {
        val query = binding.etSearch.text.toString()
        if (query.isEmpty()) return
        val toSearch = Intent(this,SearchMovieActivity::class.java)
        toSearch.putExtra(SearchMovieActivity.EXTRA_SEARCH,query)
        this.startActivity(toSearch)
    }
}