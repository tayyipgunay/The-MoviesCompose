package com.tayyipgunay.domain.repository

import com.tayyipgunay.data.remote.dto.MovieDetailDto
import com.tayyipgunay.data.remote.dto.MoviesDto
import com.tayyipgunay.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    // Film arama işlemi için fonksiyon
    suspend fun getMovies(search: String): Flow<Resource<MoviesDto>>
    // Parametre: search -> Kullanıcının aramak istediği film adı
    // Dönen değer: Resource<MoviesDto> -> API'den gelen film listesini kapsayan bir kaynak

    // Belirli bir filmin detaylarını almak için fonksiyon
    suspend fun getMovieDetail(imdbId: String):Flow< Resource<MovieDetailDto>>
    // Parametre: imdbId -> IMDb kimliği ile belirli bir filmin detaylarını alır
    // Dönen değer: Resource<MovieDetailDto> -> API'den gelen film detaylarını kapsayan bir kaynak
}