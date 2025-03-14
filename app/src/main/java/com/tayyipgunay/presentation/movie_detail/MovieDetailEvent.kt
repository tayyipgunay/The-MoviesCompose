package com.tayyipgunay.presentation.movie_detail

sealed class MovieDetailEvent {
    data class GetMovieDetailEvent(val imdbId: String) : MovieDetailEvent()

}