package com.tayyipgunay.presentation.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tayyipgunay.domain.model.MovieDetail
import com.tayyipgunay.domain.use_case.get_movie_details.GetMoviesDetailsUseCase
import com.tayyipgunay.presentation.movies.MoviesEvent
import com.tayyipgunay.presentation.movies.MoviesState
import com.tayyipgunay.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMoviesDetailsUseCase: GetMoviesDetailsUseCase
):ViewModel() {
    private val _state= MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    private var job: Job? = null

  /*  fun GetMovieDetail(imdbId: String) {
    job?.cancel() // Önceki job'ı iptal et
    job = viewModelScope.launch {
        getMoviesDetailsUseCase.executeDetailMovies(imdbId)
            .flowOn(Dispatchers.IO) // API çağrılarını IO thread'inde çalıştır
            .onEach { resource ->
                _state.value = when (resource) {
                    is Resource.Success -> MovieDetailState(movie = resource.data)
                    is Resource.Error -> MovieDetailState(error = resource.message ?: "Unexpected Error")
                    is Resource.Loading -> MovieDetailState(isLoading = true)
                }
            }
            .launchIn(this) // launchIn() içinde coroutine başlat
    }
}*/
  fun getMovieDetail(imdbId: String) { // Küçük harfle başlamalı
      job?.cancel() // Önceki iş iptal edilir
      MovieDetailState(isLoading = true)
      job = viewModelScope.launch {
          getMoviesDetailsUseCase.executeDetailMovies(imdbId)
              .flowOn(Dispatchers.IO) // API çağrıları için IO thread kullanımı
              .collect { resource -> // launchIn yerine collect kullanılmalı
                  _state.update {
                      when (resource) {
                          is Resource.Success -> it.copy(movie = resource.data, isLoading = false)
                          is Resource.Error -> it.copy(error = resource.message ?: "Beklenmeyen bir hata oluştu.",
                              isLoading = false)
                          is Resource.Loading -> it.copy(isLoading = true)
                      }
                  }
              }
      }
  }
}

fun onEvent(event: MoviesEvent){
    when(event){
        is MoviesEvent.Search -> {
          //  G(event.search)

        }
    }
}
