package com.geniusforapp.movies.domain.usecases

import com.geniusforapp.movies.R
import com.geniusforapp.movies.domain.entities.Movie
import com.geniusforapp.movies.domain.entities.Movies
import com.geniusforapp.movies.domain.repositories.MoviesRepositories

typealias MoviesUseCase = suspend () -> List<MoviesItems>


suspend fun getMovieUseCase(
    id: Int,
    moviesRepositories: MoviesRepositories = MoviesRepositories()
): Movie = moviesRepositories.getMovie(id)

suspend fun getPopularMoviesUseCase(
    page: Int,
    moviesRepositories: MoviesRepositories = MoviesRepositories()
): List<Movies.Movie> = moviesRepositories.getPopularMovies(page)
    .results

suspend fun getLatestMoviesUseCase(
    moviesRepositories: MoviesRepositories = MoviesRepositories()
): List<Movies.Movie> = moviesRepositories.getLatestMovies()
    .results

suspend fun getTopRatedMoviesUseCase(
    page: Int,
    moviesRepositories: MoviesRepositories = MoviesRepositories()
): List<Movies.Movie> = moviesRepositories.getTopRatedMovies(page)
    .results


data class MoviesItems(val id: Int, val movies: List<Movies.Movie>)

suspend fun getMoviesUseCase(moviesRepositories: MoviesRepositories = MoviesRepositories()): List<MoviesItems> {
    val movies = mutableListOf<MoviesItems>()
    movies.add(MoviesItems(R.string.popular, moviesRepositories.getPopularMovies(1).results))
    movies.add(MoviesItems(R.string.latest, moviesRepositories.getLatestMovies().results))
    movies.add(MoviesItems(R.string.upcoming, moviesRepositories.getUpComing(1).results))
    movies.add(MoviesItems(R.string.top_rated, moviesRepositories.getTopRatedMovies(1).results))
    return movies
}