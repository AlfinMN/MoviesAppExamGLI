package com.examproject.myapplication.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.examproject.myapplication.R
import com.examproject.myapplication.config.MoviesApp
import com.examproject.myapplication.databinding.ActivityMovieByGenreBinding
import com.examproject.myapplication.movies.data.MovieList
import com.examproject.myapplication.movies.data.MoviesModel
import com.examproject.myapplication.movies.data.MoviesViewModel
import com.examproject.myapplication.movies.data.adapter.AdapterMovies
import com.examproject.myapplication.utils.CheckValidation
import com.examproject.myapplication.utils.MessageLog
import com.examproject.myapplication.utils.PaginationScrollListener
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class MovieByGenreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieByGenreBinding

    @Inject
    lateinit var moviesViewModel: MoviesViewModel

    private lateinit var mAdapter: AdapterMovies
    private val pageStart: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPages: Int = 1
    private var currentPage: Int = pageStart

    private var idGenre : Int = 0
    private var nameGenre : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieByGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (this.applicationContext as MoviesApp).applicationComponent.inject(this)
        idGenre = intent.getIntExtra(EXTRA_ID,0)
        nameGenre = intent.getStringExtra(EXTRA_NAME).toString()

        binding.tvGenre.setText(nameGenre)

        initMyOrderRecyclerView()
        observerDataRequest()

    }

    private fun observerDataRequest() {
        moviesViewModel.topMoviesFirstPageResponse.observe(this) {
            if (it is MoviesModel) {
                hideErrorView()
                val results: MutableList<MovieList> = fetchResults(it) as MutableList<MovieList>
                binding.mainProgress.visibility = View.GONE
                mAdapter.addAll(results)
                totalPages = it.totalPages!!

                if (currentPage <= totalPages) mAdapter.addLoadingFooter()
                else isLastPage = true
            }else if (it is Throwable){
                showErrorView(it)
            }else{
                MessageLog.setLogCat("TAG_TEST" , "Error First Page")
            }
        }

        moviesViewModel.topMoviesNextPageResponse.observe(this) {
            if (it is MoviesModel) {

                val results = fetchResults(it) as MutableList<MovieList>
                mAdapter.removeLoadingFooter()
                isLoading = false
                mAdapter.addAll(results)

                if (currentPage != totalPages) mAdapter.addLoadingFooter()
                else isLastPage = true

            }else if (it is Throwable){
                mAdapter.showRetry(true, fetchErrorMessage(it))
            }else{
                MessageLog.setLogCat("TAG_TEST" , "Error First Page")
            }

        }
    }

    private fun initMyOrderRecyclerView() {
        //attach adapter to  recycler
        mAdapter = AdapterMovies(this)
        binding.adapterTopMovies = mAdapter
        binding.recyclerMovies.setHasFixedSize(true)
        binding.recyclerMovies.itemAnimator = DefaultItemAnimator()

        loadFirstPage()

        binding.recyclerMovies.addOnScrollListener(object : PaginationScrollListener(binding.recyclerMovies.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1

                Handler(Looper.myLooper()!!).postDelayed({
                    loadNextPage()
                }, 1000)
            }

            override fun getTotalPageCount(): Int {
                return totalPages
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
    }

    private fun loadFirstPage() {
        hideErrorView()
        if (CheckValidation.isConnected(this)) {
            moviesViewModel.requestFirstPageTopMovies(idGenre,currentPage,this)
        }else{
            showErrorView(null)
        }
    }

    private fun showErrorView(throwable: Throwable?) {
        if (binding.lyError.errorLayout.visibility == View.GONE) {
            binding.lyError.errorLayout.visibility = View.VISIBLE
            binding.mainProgress.visibility = View.GONE

            if (!CheckValidation.isConnected(this)) {
                binding.lyError.errorTxtCause.setText(R.string.error_msg_no_internet)
            } else {
                if (throwable is TimeoutException) {
                    binding.lyError.errorTxtCause.setText(R.string.error_msg_timeout)
                } else {
                    binding.lyError.errorTxtCause.setText(R.string.error_msg_unknown)
                }
            }
        }
    }

    private fun fetchResults(moviesModel: MoviesModel): List<MovieList>? {
        return moviesModel.results
    }

    private fun hideErrorView() {
        if (binding.lyError.errorLayout.visibility == View.VISIBLE) {
            binding.lyError.errorLayout.visibility = View.GONE
            binding.mainProgress.visibility = View.VISIBLE
        }
    }

    fun loadNextPage() {
        if (CheckValidation.isConnected(this)) {
            moviesViewModel.requestFirstNextPageMovies(idGenre,currentPage,this)
        }else{
            mAdapter.showRetry(true, fetchErrorMessage(null))
        }
    }

    private fun fetchErrorMessage(throwable: Throwable?): String {
        var errorMsg: String = resources.getString(R.string.error_msg_unknown)

        if (!CheckValidation.isConnected(this)) {
            errorMsg = resources.getString(R.string.error_msg_no_internet)
        } else if (throwable is TimeoutException) {
            errorMsg = resources.getString(R.string.error_msg_timeout)
        }

        return errorMsg
    }

    companion object {
        const val EXTRA_ID = "idgenre"
        const val EXTRA_NAME = "namegenre"
    }
}