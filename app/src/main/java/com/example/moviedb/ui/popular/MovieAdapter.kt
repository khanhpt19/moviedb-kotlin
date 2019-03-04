package com.example.moviedb.ui.popular

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.ItemMovieBinding
import com.example.moviedb.ui.base.BaseAdapter

class MovieAdapter(val itemClick: (Movie) -> Unit = {}) :
    BaseAdapter<Movie, ItemMovieBinding>(object : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title && oldItem.posterPath == newItem.posterPath
                    && oldItem.releaseDate == newItem.releaseDate && oldItem.voteAverage == newItem.voteAverage
        }
    }) {
    override fun getLayout(viewType: Int): Int {
        return R.layout.item_movie
    }

    override fun itemBinding(binding: ItemMovieBinding) {
        binding.apply {
            root.setOnClickListener {
                item?.apply {
                    itemClick(this)
                }
            }
        }
    }
}
