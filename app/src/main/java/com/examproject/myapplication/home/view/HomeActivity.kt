package com.examproject.myapplication.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.examproject.myapplication.R
import com.examproject.myapplication.config.MoviesApp
import com.examproject.myapplication.databinding.ActivityHomeBinding
import com.examproject.myapplication.movies.data.MoviesViewModel
import com.examproject.myapplication.movies.data.adapter.GenreAdapter
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var moviesViewModel: MoviesViewModel
    lateinit var genreAdapter: GenreAdapter

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
        }
    }
    private fun showLoad(state : Boolean){
        if (state){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }
}