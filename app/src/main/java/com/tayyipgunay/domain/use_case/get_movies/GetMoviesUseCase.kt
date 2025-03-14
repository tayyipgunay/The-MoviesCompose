package com.tayyipgunay.domain.use_case.get_movies

import com.tayyipgunay.data.remote.dto.toMovieList
import com.tayyipgunay.domain.model.Movie
import com.tayyipgunay.domain.repository.MovieRepository
import com.tayyipgunay.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun executeGetMovies(search: String): Flow<Resource<List<Movie>>> {
        return repository.getMovies(search)
            .map { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val movies = resource.data?.toMovieList()
                        movies?.let { Resource.Success(it) }
                            ?: Resource.Error("Film listesi dönüştürülemedi.")
                    }
                    is Resource.Error -> Resource.Error(resource.message ?: "Film verisi alınırken bir hata oluştu.")
                    is Resource.Loading -> Resource.Loading()
                }
            }
    }

}
