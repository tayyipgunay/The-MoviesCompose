package com.tayyipgunay.presentation.movies.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.tayyipgunay.domain.model.Movie
import com.tayyipgunay.presentation.MoviesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.tayyipgunay.themoviescompose2.R


@Composable
fun MovieSearchScreen(navController: NavController
                  //    moviesViewModel: MoviesViewModel = viewModel()
) {

    val moviesViewModel: MoviesViewModel = hiltViewModel()


    val state by moviesViewModel.state.collectAsState()

    println("merhaba1")
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // **1️⃣ Arama Çubuğu**
        SearchBar { query ->
            moviesViewModel.getMovies(query)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // **2️⃣ Yüklenme Animasyonu**
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // **3️⃣ Hata Mesajı**
        if (state.error.isNotEmpty()) {
            Text(
                text = state.error,
                color = Color.Red,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // **5️⃣ Film Listesi**
        LazyColumn {
            items(state.movies) { movie ->
                MovieItem(movie, onClick = {
                    navController.navigate("movie_detail_screen/${movie.imdbID}")

                }
                )
            }
        }
    }
}


@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = {
            query = it
            onSearch(it) // Kullanıcı her karakter girdiğinde aramayı güncelle
            // boşsa hiçbir işlem yapılmaz

        },
        placeholder = { Text("Search movies...") },
        modifier = Modifier
            .fillMaxWidth()
            . padding(8.dp),
        singleLine = true,

        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    query = "" // TextField içindeki metni sıfırla (arama kutusu temizlenir)
                   onSearch("") //onValueChange tetiklemiyor bu yüzden manuel olarak tetikleniyor
                }
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        }
    )
}


    @Composable
    fun MovieItem(movie: Movie, onClick: (Movie) -> Unit = {}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    onClick(movie)
                }

        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                AsyncImage(
                    model = movie.poster,
                    contentDescription = "Movie Poster",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(movie.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(movie.year, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }




