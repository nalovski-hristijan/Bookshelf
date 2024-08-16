package com.hnalovski.bookshelf.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.hnalovski.bookshelf.components.BookTopBar
import com.hnalovski.bookshelf.components.InputField
import com.hnalovski.bookshelf.model.Item
import com.hnalovski.bookshelf.navigation.BookScreens

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current


    Scaffold(topBar = {
        BookTopBar(title = "Search", isHomeScreen = true)
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                InputField(
                    valueState = searchQueryState,
                    labelId = "Search Books",
                    onAction = KeyboardActions {
                        // after search is done clear the input field and hide the keyboard
                        viewModel.searchBooks(searchQueryState.value.trim())
                        searchQueryState.value = ""
                        keyboardController?.hide()
                    })
                BookGrid(navController = navController, viewModel = viewModel)
            }
        }

    }
}



@Composable
fun BookGrid(navController: NavController, viewModel: HomeScreenViewModel) {
    val listOfBooks = viewModel.list

    if (viewModel.isLoading) {
        LinearProgressIndicator()
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(items = listOfBooks) { book ->
                BookImage(navController = navController, book = book)
            }
        }
    }
}



@Composable
fun BookImage(navController: NavController, book: Item) {
    AsyncImage(
        model = book.volumeInfo.imageLinks.thumbnail,
        contentDescription = book.volumeInfo.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(300.dp)
            .clickable {
                navController.navigate(BookScreens.DetailsScreen.name + "/${book.id}")
            }
    )
}

