package com.omarelrayes.moviedb.features.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.omarelrayes.moviedb.R
import com.omarelrayes.moviedb.core.api.ApiConstants
import com.omarelrayes.moviedb.databinding.ItemMovieBinding
import com.omarelrayes.moviedb.features.list.domain.Movie

class MoviesAdapter(
    private val onMovieClick: (Movie) -> Unit
) :
    PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(Comparator) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie -> holder.bind(movie) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MovieViewHolder(binding)
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.poster.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { movie ->
                    onMovieClick(
                        movie
                    )
                }
            }
        }

        fun bind(movie: Movie) {
            binding.poster.load(ApiConstants.getFullImagePath(movie.image)) {
                error(R.drawable.ic_error)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) SHOW_ITEM else LOADING_ITEM
    }

    object Comparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }

    companion object {
        private const val SHOW_ITEM = 0
        const val LOADING_ITEM = 1
    }
}
