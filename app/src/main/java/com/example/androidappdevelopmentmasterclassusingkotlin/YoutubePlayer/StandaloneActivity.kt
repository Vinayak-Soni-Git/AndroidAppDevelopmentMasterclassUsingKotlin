package com.example.androidappdevelopmentmasterclassusingkotlin.YoutubePlayer

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.androidappdevelopmentmasterclassusingkotlin.R
import com.google.android.youtube.player.YouTubeStandalonePlayer

class StandaloneActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_standalone)

        val btnPlayVideo: Button = findViewById(R.id.btnPlayVideo)
        val btnPlayPlaylist: Button = findViewById(R.id.btnPlayPlaylist)
        btnPlayVideo.setOnClickListener(this)
        btnPlayPlaylist.setOnClickListener(this)

//        btnPlayVideo.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(p0: View?) {
//                TODO("Not yet implemented")
//            }
//        })

//        btnPlayVideo.setOnClickListener(View.OnClickListener{view ->
//
//        })

//        val listener = View.OnClickListener { view ->
//
//        }
//        btnPlayVideo.setOnClickListener(listener)
//        btnPlayPlaylist.setOnClickListener(listener)
    }

    override fun onClick(view: View?) {
        val intent = when (view?.id) {
            R.id.btnPlayVideo -> YouTubeStandalonePlayer.createVideoIntent(
                this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_VIDEO_ID, 0, true, false
            )

            R.id.btnPlayPlaylist -> YouTubeStandalonePlayer.createPlaylistIntent(
                this, getString(R.string.GOOGLE_API_KEY), YOUTUBE_PLAYLIST, 0, 0, true, true
            )

            else -> throw IllegalArgumentException("Undefined button clicked")
        }
        startActivity(intent)
    }
}