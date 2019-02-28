package com.example.moviedb.ui.popular

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.ItemMovieBinding
import com.example.moviedb.ui.base.BaseAdapter

class MovieAdapter : BaseAdapter<Movie, ItemMovieBinding>(object : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id && oldItem.title == newItem.title
    }
}) {
    override fun getLayout(viewType: Int): Int {
        return R.layout.item_movie
    }

}
