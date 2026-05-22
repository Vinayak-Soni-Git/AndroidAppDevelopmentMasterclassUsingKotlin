package com.example.androidappdevelopmentmasterclassusingkotlin.PhotoBrowserApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidappdevelopmentmasterclassusingkotlin.R
import com.squareup.picasso.Picasso

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail: ImageView = view.findViewById(R.id.iv_thumbnail)
    var title: TextView = view.findViewById(R.id.tv_image_title)
}

class FlickrRVAdaptor(private var photoList: List<Photo>) :
    RecyclerView.Adapter<FlickrImageViewHolder>() {
    private val TAG = "FlickRVAdaptor"
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlickrImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return FlickrImageViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FlickrImageViewHolder,
        position: Int
    ) {
        val photoItem = photoList[position]
        Picasso.with(holder.thumbnail.context).load(photoItem.image)
            .error(R.drawable.image_picture_svgrepo_com)
            .placeholder(R.drawable.image_picture_svgrepo_com)
            .into(holder.thumbnail)

        holder.title.text = photoItem.title

    }

    fun loadNewData(newPhotos: List<Photo>) {
        photoList = newPhotos
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): Photo? {
        return if (photoList.isNotEmpty()) photoList[position] else null
    }

    override fun getItemCount(): Int {
        return if (photoList.isNotEmpty()) photoList.size else 0
    }
}