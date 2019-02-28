package com.example.moviedb.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageSrc")
fun imageSrc(imageView: ImageView, url: String){
    Glide.with(imageView.context).load(url).into(imageView)
}
