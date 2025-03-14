package com.tayyipgunay.domain.use_case.get_movie_details

import com.tayyipgunay.data.remote.dto.toMovieDetail
import com.tayyipgunay.domain.model.MovieDetail
import com.tayyipgunay.domain.repository.MovieRepository
import com.tayyipgunay.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


class GetMoviesDetailsUseCase @Inject constructor(private val repository: MovieRepository) {

    // Belirli bir filmin detaylarını almak için kullanılan UseCase sınıfı
    suspend fun executeDetailMovies(imdbId: String): Flow<Resource<MovieDetail>> {
        return repository.getMovieDetail(imdbId)
            .map { result ->
                when (result) {
                    is Resource.Success -> {
                        val movieDetail = result.data?.toMovieDetail()
                        movieDetail?.let { Resource.Success(it) }
                            ?: Resource.Error("Film detayları dönüştürülemedi.")
                    }

                    is Resource.Error -> Resource.Error(result.message ?: "Bir hata oluştu.")
                    is Resource.Loading -> Resource.Loading()
                }
            }
            .catch { e -> emit(Resource.Error("Beklenmedik hata: ${e.message}")) } // Hata yakalama
    }
}