package com.tayyipgunay.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tayyipgunay.domain.use_case.get_movies.GetMoviesUseCase
import com.tayyipgunay.presentation.movie_detail.MovieDetailState
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
class MoviesViewModel @Inject constructor
    (private val getMoviesUseCase: GetMoviesUseCase):
    ViewModel() {
    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state.asStateFlow()




    private var job: Job? = null

    init {
        getMovies(_state.value.search)
    }

   /* fun getMovies(search: String) {
        job?.cancel() // Önceki job'ı iptal et
        job = viewModelScope.launch {
            getMoviesUseCase.executeGetMovies(search)
                .flowOn(Dispatchers.IO) // API çağrılarını IO thread'inde çalıştır
                .onEach { resource ->
                    _state.value = when (resource) {
                        is Resource.Success -> MoviesState(movies = resource.data ?: emptyList())
                        is Resource.Error -> MoviesState(error = resource.message ?: "Unexpected Error")
                        is Resource.Loading -> MoviesState(isLoading = true)
                    }
                }
                .launchIn(this) // launchIn() içinde coroutine başlat
        }
    }*/
   fun SearchisBlank(){
       _state.update { it.copy(movies = emptyList(), isLoading = false, error = "A Search Movie") }
   }

    fun getMovies(search: String){
        job?.cancel()
        if (search.isBlank()) {
            SearchisBlank()
            return
        }

       _state.update { it.copy(isLoading = true) }
       job = viewModelScope.launch {
            getMoviesUseCase.executeGetMovies(search)
                .flowOn(Dispatchers.IO)
                .collect{resource->
                   _state.update {currentState->
                       when(resource){
                           is Resource.Success -> currentState.copy(movies = resource.data ?: emptyList(), isLoading = false, error = "")
                           is Resource.Error -> currentState.copy(movies = emptyList(),error = resource.message ?: "Unexpected Error", isLoading = false)
                           is Resource.Loading -> currentState.copy(isLoading = true)
                       }
                   }
                }
        }
    }

    fun onEvent(event: MoviesEvent){
        when(event){
            is MoviesEvent.Search -> {
                getMovies(event.search)
            }
        }
    }
}



