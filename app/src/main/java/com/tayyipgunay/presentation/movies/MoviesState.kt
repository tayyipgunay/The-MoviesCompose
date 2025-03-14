package com.tayyipgunay.presentation.movies

import com.tayyipgunay.domain.model.Movie

data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",
    val search : String = ""

)