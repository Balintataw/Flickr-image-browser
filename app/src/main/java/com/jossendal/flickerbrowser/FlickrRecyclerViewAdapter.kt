package com.jossendal.flickerbrowser

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

class FlickrRecyclerViewAdapter(private var photoList : List<Photo>) : RecyclerView.Adapter<FlickrImageViewHolder>(){
    private val TAG = "FlickrRecyclerAdapter"

    override fun getItemCount(): Int {
//        Log.d(TAG, "getItemCount called")
        return if(photoList.isNotEmpty()) photoList.size else 1
    }

    fun loadNewData(newPhotos: List<Photo>) {
        photoList = newPhotos
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int) : Photo? {
        return if(photoList.isNotEmpty()) photoList[position] else null
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        // called by layout manager when it wants new data in an existing view
        if(photoList.isEmpty()) {
            holder.thumbnail.setImageResource(R.drawable.baseline_image_white_48dp)
            holder.title.setText(R.string.empty_photo)
        } else {
            val photoItem = photoList[position]
            Log.d(TAG, "onBindViewHolder called: ${photoItem.title} --> $position")
            Picasso.get().load(photoItem.photoUrl)
                .error(R.drawable.baseline_image_white_48dp_lg)
                .placeholder(R.drawable.baseline_image_white_48dp_lg)
                .into(holder.thumbnail)

            holder.title.text = photoItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        // Called by layout manager when new view is needed
        Log.d(TAG, "onCreateViewHolder new view requested")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return FlickrImageViewHolder(view)
    }
}