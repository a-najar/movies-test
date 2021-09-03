package com.geniusforapp.movies.domain.repositories

import com.geniusforapp.movies.domain.entities.Movie
import com.geniusforapp.movies.domain.entities.Movies
import com.geniusforapp.movies.domain.gateways.MoviesGateway

interface MoviesRepositories {
    suspend fun getLatestMovies(): Movies
    suspend fun getPopularMovies(page: Int): Movies
    suspend fun getTopRatedMovies(page: Int): Movies
    suspend fun getMovie(id: Int): Movie
    suspend fun getUpComing(page: Int): Movies
}


class MoviesRepositoriesImpl(private val moviesGateway: MoviesGateway = MoviesGateway()) :
    MoviesRepositories {
    override suspend fun getLatestMovies(): Movies = moviesGateway.getLatestMovies()

    override suspend fun getPopularMovies(page: Int): Movies = moviesGateway.getPopularMovies(page)
    override suspend fun getTopRatedMovies(page: Int): Movies =
        moviesGateway.getTopRatedMovies(page)

    override suspend fun getUpComing(page: Int): Movies = moviesGateway.getUpComing(page)

    override suspend fun getMovie(id: Int): Movie = moviesGateway.getMovie(id)

}


@Suppress("FunctionName")
fun MoviesRepositories(): MoviesRepositories = MoviesRepositoriesImpl()