package com.geniusforapp.movies.domain.gateways

import com.geniusforapp.movies.BuildConfig
import com.geniusforapp.movies.domain.entities.Genres
import com.geniusforapp.movies.domain.entities.Movie
import com.geniusforapp.movies.domain.entities.Movies
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val API_KEY = "api_key"
private const val LANGUAGE = "language"

interface MoviesGateway {

    @GET("genre/movie/list")
    suspend fun getGeneres(): Genres

    @GET("movie/now_playing")
    suspend fun getLatestMovies(): Movies

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Movies

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Movies

    @GET("movie/upcoming")
    suspend fun getUpComing(@Query("page") page: Int): Movies

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id: Int): Movie
}


@Suppress("FunctionName")
fun MoviesGateway(): MoviesGateway = Retrofit.Builder()
    .baseUrl(BuildConfig.API_URL)
    .client(CreateMoviesClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(MoviesGateway::class.java)


@Suppress("FunctionName")
internal fun CreateMoviesClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(MoviesInterceptor())
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

class MoviesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_TOKEN)
            .addQueryParameter(LANGUAGE, "en-US")
            .build()
        newRequest.url(newUrl)
        return chain.proceed(newRequest.build())
    }
}
