package com.tayyipgunay.data.dependencyinjection

import android.content.Context
import com.tayyipgunay.data.remote.MovieAPI
import com.tayyipgunay.data.repository.MovieRepositoryIMPL
import com.tayyipgunay.domain.repository.MovieRepository
import com.tayyipgunay.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieAPI): MovieRepository {
        return MovieRepositoryIMPL(api)
    }
}


