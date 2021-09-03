package com.geniusforapp.movies.domain.entities


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Genres(
    @SerializedName("genres")
    val genres: List<Genre> = listOf()
) {
    @Keep
    data class Genre(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = ""
    )
}