package com.example.moviedb.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageSrc")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(Constants.BASE_IMAGE_URL + url).into(imageView)
}
