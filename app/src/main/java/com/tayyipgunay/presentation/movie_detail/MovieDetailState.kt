package com.tayyipgunay.presentation.movie_detail

import com.tayyipgunay.domain.model.MovieDetail

data class MovieDetailState(
    val isLoading : Boolean = false,
    val movie : MovieDetail? = null,
    val error : String = ""
)
