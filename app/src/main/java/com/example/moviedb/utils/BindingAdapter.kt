package com.example.moviedb.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.ui.popular.EndLessRecyclerOnScrollListener


@BindingAdapter("imageSrc")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(Constants.BASE_IMAGE_URL + url).into(imageView)
}

@BindingAdapter("setOnScroll")
fun setScroll(
    recyclerView: RecyclerView,
    listener: EndLessRecyclerOnScrollListener
) {
    recyclerView.addOnScrollListener(listener)
}
