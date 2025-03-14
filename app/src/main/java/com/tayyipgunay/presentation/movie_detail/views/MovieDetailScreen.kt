package com.tayyipgunay.presentation.movie_detail.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tayyipgunay.presentation.movie_detail.MovieDetailViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter


@Composable
fun MovieDetailScreen(imdbId: String) {


   val movieDetailViewModel: MovieDetailViewModel = hiltViewModel()

    LaunchedEffect(imdbId){
    movieDetailViewModel.getMovieDetail(imdbId)

}
    val state by movieDetailViewModel.state.collectAsState()




    println("merhabaa2  ${state.movie?.title}")
    println("merhbaaa2")


    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black), contentAlignment = Alignment.Center
    ) {
         state.movie?.let {
             Column(
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally
             ) {
                 AsyncImage(
                     model = it!!.poster,
                     contentDescription = "Movie Poster",
                     contentScale = ContentScale.Crop,
                     modifier = Modifier
                         .padding(16.dp)
                         .size(300.dp, 300.dp)
                         .clip(RectangleShape)
                         .align(CenterHorizontally)
                 )

                 Text(
                     text = it.title,
                     textAlign = TextAlign.Center,
                     modifier = Modifier.padding(14.dp),
                     color = Color.White
                 )

                 Text(
                     text = it!!.year,
                     textAlign = TextAlign.Center,
                     modifier = Modifier.padding(14.dp),
                     color = Color.White
                 )

                 Text(
                     text = it!!.actors,
                     textAlign = TextAlign.Center,
                     modifier = Modifier.padding(14.dp),
                     color = Color.White
                 )

                 Text(
                     text = it!!.country,
                     textAlign = TextAlign.Center,
                     modifier = Modifier.padding(14.dp),
                     color = Color.White
                 )

                 Text(
                     text = "Director: ${it.director}",
                     textAlign = TextAlign.Center,
                     modifier = Modifier.padding(14.dp),
                     color = Color.White
                 )

                 Text(
                     text = "IMDB Rating: ${it!!.imdbRating}",
                     textAlign = TextAlign.Center,
                     modifier = Modifier.padding(14.dp),
                     color = Color.White
                 )

             }
         }


                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                            .align(Alignment.Center)
                    )
                }

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }


        }



