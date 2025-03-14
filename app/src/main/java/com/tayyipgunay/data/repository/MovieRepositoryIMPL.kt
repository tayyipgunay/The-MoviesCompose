package com.tayyipgunay.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.tayyipgunay.data.remote.MovieAPI
import com.tayyipgunay.data.remote.dto.MovieDetailDto
import com.tayyipgunay.data.remote.dto.MoviesDto
import com.tayyipgunay.domain.repository.MovieRepository
import com.tayyipgunay.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


class MovieRepositoryIMPL @Inject constructor(private val api: MovieAPI): MovieRepository {


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getMovies(search: String): Flow<Resource<MoviesDto>> = flow {
        emit(Resource.Loading()) // Önce Loading durumu yay

        try {
            val response = api.getMovies(search)

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    if (!responseBody.search.isNullOrEmpty()) {
                        emit(Resource.Success(responseBody)) // Başarılı veri yay
                    } else {
                        emit(Resource.Error("No Games Found.")) // Sonuç bulunamadı
                    }
                } ?: emit(Resource.Error("No Games Found")) // Yanıt boş
            }
        } catch (e: IOException) {
            // 🔹 İnternet bağlantısı yoksa özel hata mesajı
            emit(Resource.Error("No internet connection. Please check your network."))
        } catch (e: HttpException) {
            // 🔹 API hatalarını yakalamak için
            emit(Resource.Error("Server error: ${e.message}"))
        } catch (e: Exception) {
            // 🔹 Beklenmeyen hataları yakalamak için
            emit(Resource.Error("Unexpected Error: ${e.message}"))
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getMovieDetail(imdbId: String): Flow<Resource<MovieDetailDto>> = flow {
        emit(Resource.Loading()) // 🔹 Önce Loading durumu yay

        try {
            val response = api.getMovieDetail(imdbId)

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    if (!responseBody.response.isNullOrEmpty()) {
                        emit(Resource.Success(responseBody)) // 🔹 Başarılı veri yay
                    } else {
                        emit(Resource.Error("No movie details found."))
                    }
                } ?: emit(Resource.Error("No movie details found.")) // Yanıt boş
            } else {
                emit(Resource.Error("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection. Please check your network.")) // 🔹 İnternet bağlantısı hatası
        } catch (e: HttpException) {
            emit(Resource.Error("Server error: ${e.message}")) // 🔹 API sunucu hatası
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected Error: ${e.message}")) // 🔹 Genel hata yönetimi
        }
    }
}
/*
/*override suspend fun getMovies(search: String): Resource<MoviesDto> {
       val response = api.getMovies(search)
       return response.body()?.let {responseBody->
           Resource.Success(responseBody)
       } ?: Resource.Error("Hata kodu: ${response.code()} - ${response.message()}")
   }*/
 */