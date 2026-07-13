package com.example.androidappdevelopmentmasterclassusingkotlin.PhotoBrowserApp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidappdevelopmentmasterclassusingkotlin.R
import com.squareup.picasso.Picasso

class PhotoDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_photo_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activateToolbar(true)
//        val photo = intent.getSerializableExtra(PHOTO_TRANSFER) as Photo
        val photo = intent.extras?.getParcelable<Photo>(PHOTO_TRANSFER) as Photo

        val photoImage: ImageView = findViewById(R.id.iv_photo_image)
        val photoTitle: TextView = findViewById(R.id.tv_photo_title)
        val photoTags: TextView = findViewById(R.id.tv_photo_tag)
        val photoAuthor: TextView = findViewById(R.id.tv_photo_author)

        photoTitle.text = photo.title
        photoTags.text = photo.tags
        photoAuthor.text = photo.author

        Picasso.with(this).load(photo.link)
            .error(R.drawable.image_picture_svgrepo_com)
            .placeholder(R.drawable.image_picture_svgrepo_com)
            .into(photoImage)
    }
}