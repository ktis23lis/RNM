package com.example.rnm.feature.interf

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object AddImage {

     fun loadingImage(string: String, imageView: ImageView) {
        Picasso.get().load(string).into(
            imageView, object : Callback {
                override fun onSuccess() {
                    Log.d("OK", "OK")
                }

                override fun onError(e: Exception?) {
                    Log.d("Error", "Error")
                }
            })
    }
}