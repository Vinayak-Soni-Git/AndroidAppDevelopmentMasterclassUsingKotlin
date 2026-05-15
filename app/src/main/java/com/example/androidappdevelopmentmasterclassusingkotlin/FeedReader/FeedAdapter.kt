package com.example.androidappdevelopmentmasterclassusingkotlin.FeedReader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.androidappdevelopmentmasterclassusingkotlin.R

class ViewHolder(v: View) {
    val tvName: TextView = v.findViewById(R.id.tv_name)
    val tvArtist: TextView = v.findViewById(R.id.tv_artist)
    val tvSummary: TextView = v.findViewById(R.id.tv_summary)

}

class FeedAdapter(
    context: Context,
    private val resource: Int,
    private val applications: List<FeedEntry>
) :
    ArrayAdapter<FeedEntry>(context, resource) {
    private val TAG = "FeedAdapter"
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return applications.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

//        val tvName: TextView = view.findViewById(R.id.tv_name)
//        val tvArtist: TextView = view.findViewById(R.id.tv_artist)
//        val tvSummary: TextView = view.findViewById(R.id.tv_summary)
//
        val currentApp = applications[position]
//        tvName.text = currentApp.name
//        tvArtist.text = currentApp.artist
//        tvSummary.text = currentApp.summary

        viewHolder.tvName.text = currentApp.name
        viewHolder.tvArtist.text = currentApp.artist
        viewHolder.tvSummary.text = currentApp.summary

        return view
    }
}