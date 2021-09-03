package com.geniusforapp.movies.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.geniusforapp.movies.BuildConfig
import com.geniusforapp.movies.R
import com.geniusforapp.movies.databinding.ItemHorizontalMovieBinding
import com.geniusforapp.movies.databinding.ItemMoviesListBinding
import com.geniusforapp.movies.domain.entities.Movies.Movie
import com.geniusforapp.movies.ui.home.adapter.HomeListItem.MoviesListItem


sealed class HomeListItem(open val type: Int) {
    data class MoviesListItem(val title: Int, val movie: List<Movie>) :
        HomeListItem(R.id.item_movies)
}


typealias OnMovieClicked = (Int) -> Unit

class HomeListAdapter(private val onMovieClicked: OnMovieClicked) :
    ListAdapter<HomeListItem, HomeListViewHolder<*>>(HomeListDifCallback()) {

    private val items = mutableListOf<HomeListItem>()


    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListViewHolder<*> {
        return when (viewType) {
            R.id.item_movies -> MoviesListViewHolder(
                ItemMoviesListBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onMovieClicked
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: HomeListViewHolder<*>, position: Int) {
        when (getItemViewType(position)) {
            R.id.item_movies -> (holder as MoviesListViewHolder).bind(getItem(position) as MoviesListItem)
        }
    }

    fun addItem(homeListItem: HomeListItem) {
        items.add(homeListItem)
        submitList(items)
    }

}


class MoviesAdapter(private val onMovieClicked: OnMovieClicked) :
    ListAdapter<Movie, MoviesViewHolder>(MoviesDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            onMovieClicked,
            ItemHorizontalMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

class MoviesViewHolder(
    private val onMovieClicked: OnMovieClicked,
    private val binding: ItemHorizontalMovieBinding
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie?) {
        binding.cardPoster.setOnClickListener { item?.id?.let(onMovieClicked) }
        binding.imageCard.load(BuildConfig.IMAGE_URL.plus(item?.backdropPath))
        binding.textTitle.text = item?.title
    }
}

abstract class HomeListViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}

class MoviesListViewHolder(
    private val binding: ItemMoviesListBinding,
    private val onMovieClicked: OnMovieClicked
) :
    HomeListViewHolder<MoviesListItem>(binding.root) {
    override fun bind(item: MoviesListItem) {
        binding.textName.setText(item.title)
        binding.listMovies.adapter = MoviesAdapter(onMovieClicked).also {
            it.submitList(item.movie)
        }
    }
}

class HomeListDifCallback : DiffUtil.ItemCallback<HomeListItem>() {
    override fun areItemsTheSame(oldItem: HomeListItem, newItem: HomeListItem): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: HomeListItem, newItem: HomeListItem): Boolean {
        return oldItem == newItem
    }
}