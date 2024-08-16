package com.hnalovski.bookshelf.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.hnalovski.bookshelf.components.BookTopBar
import com.hnalovski.bookshelf.data.DataOrException
import com.hnalovski.bookshelf.model.Item
import com.hnalovski.bookshelf.navigation.BookScreens

@Composable
fun DetailsScreen(
    navController: NavController,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel(),
    bookId: String
) {

    val bookInfo by produceState<DataOrException<Item, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = detailsScreenViewModel.getBookInfo(bookId = bookId)
    }



    if (bookInfo.data == null) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator()
            Text(text = "Loading..")
        }

    } else {
        Scaffold(topBar = {
            BookTopBar(title = bookInfo.data!!.volumeInfo.title, isHomeScreen = false) {
                navController.navigate(BookScreens.HomeScreen.name)
            }
        }) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        modifier = Modifier.size(300.dp),
                        painter = rememberAsyncImagePainter(model = bookInfo.data!!.volumeInfo.imageLinks.thumbnail),
                        contentDescription = "book detail image"
                    )

                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Date Published: " + bookInfo.data!!.volumeInfo.publishedDate
                    )

                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Maturity Rating: " + bookInfo.data!!.volumeInfo.maturityRating
                    )

                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Print Type: " + bookInfo.data!!.volumeInfo.printType
                    )

                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Content Version: " + bookInfo.data!!.volumeInfo.contentVersion
                    )

                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Subtitle: " + bookInfo.data!!.volumeInfo.subtitle
                    )

                    // this code is to remove the html tags from the description
                    val cleanDescription = HtmlCompat.fromHtml(
                        bookInfo.data!!.volumeInfo.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()

                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Description: $cleanDescription"
                    )


                }
            }
        }
    }


}