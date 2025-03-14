package com.tayyipgunay.themoviescompose2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tayyipgunay.presentation.MoviesViewModel
import com.tayyipgunay.presentation.movie_detail.MovieDetailViewModel
import com.tayyipgunay.presentation.movie_detail.views.MovieDetailScreen
import com.tayyipgunay.presentation.movies.views.MovieSearchScreen
import com.tayyipgunay.themoviescompose2.ui.theme.TheMoviesCompose2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieDetailViewModel: MovieDetailViewModel by viewModels<MovieDetailViewModel>()

        setContent {

            TheMoviesCompose2Theme {
                val navController = rememberNavController()


                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = "movie_search_screen"
                    ) {
                        // Arama ekranı (Liste)
                        composable("movie_search_screen") {
                            MovieSearchScreen(navController)
                        }

                        composable(route = "movie_detail_screen/{imdbId}") { backStackEntry ->
                            val imdbId = backStackEntry.arguments?.getString("imdbId") ?: ""

                            println("imdbId: $imdbId")
                            //movieDetailViewModel.GetMovieDetail(imdbId)


                            MovieDetailScreen(imdbId)
                        }

                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TheMoviesCompose2Theme {
            //    MovieSearchScreen(navController = rememberNavController())
      //      MovieDetailScreen("tt1234567")


        }
    }
}


