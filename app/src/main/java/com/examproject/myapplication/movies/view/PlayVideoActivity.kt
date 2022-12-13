package com.examproject.myapplication.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.examproject.myapplication.R
import com.examproject.myapplication.databinding.ActivityDetailMovieBinding
import com.examproject.myapplication.databinding.ActivityPlayVideoBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class PlayVideoActivity : YouTubeBaseActivity() {

    val YOUTUBE_API_KEY ="AIzaSyCu3APEbOV6-zebAGrsMbHlJ0XHVxxdnVo"
    lateinit var youtubePlayerInit : YouTubePlayer.OnInitializedListener
    private lateinit var binding: ActivityPlayVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
       val keyVideo = intent.getStringExtra(EXTRA_KEY).toString()
        binding.apply {
            title.setText(intent.getStringExtra(EXTRA_TITLE).toString())
        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(keyVideo)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@PlayVideoActivity,"Request Failed ", Toast.LENGTH_SHORT).show()
            }
          }
            btnPlay.setOnClickListener {
                trailer.initialize(YOUTUBE_API_KEY,youtubePlayerInit)
            }
        }
    }
    companion object{
        const val EXTRA_KEY ="keyvideos"
        const val EXTRA_TITLE ="titlevideos"
    }
}