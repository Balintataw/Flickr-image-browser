package com.jossendal.flickerbrowser

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

// Void is progress indicator
class GetFlickrJsonData(private val listener: OnDataAvailable) : AsyncTask<String, Void, ArrayList<Photo>>() {
    private val TAG = "getFlickrJsonData"

    interface OnDataAvailable {
        fun onDataAvailable(data: List<Photo>)
        fun onError(exception: Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(TAG, "doInBackground starting")
        val photoList = ArrayList<Photo>()

        try {
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")
            for (item in 0 until itemsArray.length()) {
                val jsonPhoto = itemsArray.getJSONObject(item)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorId = jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("tags")

                val description = jsonPhoto.getString("description")
                val mediaJson = jsonPhoto.getJSONObject("media")
                val photoUrl = mediaJson.getString("m")
                val link = photoUrl.replaceFirst("_m.jpg", "_b.jpg")

                val photoObject = Photo(title, description, author, authorId, link, tags, photoUrl)

                photoList.add(photoObject)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e(TAG, "doInBackground: error processing json data: ${e.message}")
            cancel(true)
            listener.onError(e)
        }
        Log.d(TAG, "doInBackground complete")
        return photoList
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        Log.d(TAG, "onPostExecute Starts")
        super.onPostExecute(result)

        listener.onDataAvailable(result)
        Log.d(TAG, "onPostExecute ends")
    }
}