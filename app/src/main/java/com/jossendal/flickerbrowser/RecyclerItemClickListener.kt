package com.jossendal.flickerbrowser

import android.content.Context
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class RecyclerItemClickListener(context: Context, recyclerView: RecyclerView, private val listener: OnRecyclerClickListener)
    : RecyclerView.SimpleOnItemTouchListener() {

    private val TAG = "RecyclerItemClick"

    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemDoubleClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            Log.d(TAG, "onSingleTapConf Starts: $e")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onSingleTapConf click called")
            listener.onItemClick(childView!!, recyclerView.getChildAdapterPosition(childView))
            return true
        }

//        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
//            Log.d(TAG, "onDoubleTap Starts:")
//            val childView = recyclerView.findChildViewUnder(e.y, e.y)
//            listener.onItemDoubleClick(childView!!, recyclerView.getChildAdapterPosition(childView))
//            return true
//        }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG, "onLongPress Starts:")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            Log.d(TAG, "onLongPress item click called")
            listener.onItemLongClick(childView!!, recyclerView.getChildAdapterPosition(childView))
            super.onLongPress(e)
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, "OnInterceptTouch: starts $e")
        val result = gestureDetector.onTouchEvent(e)
//        return super.onInterceptTouchEvent(rv, e)
        Log.d(TAG, "OnInterceptTouch: returns $result")
        return result
    }
}