package dev.tekofx.biblioteques.ui.components.book

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import dev.tekofx.biblioteques.model.book.Book
import dev.tekofx.biblioteques.navigation.NavScreen
import dev.tekofx.biblioteques.ui.theme.Typography

@Composable
fun BookCard(book: Book, navHostController: NavHostController) {

    Surface(
        tonalElevation = 40.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = { navHostController.navigate("${NavScreen.BooksScreen.name}/${book.id}") }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = book.image, // Ajusta con tu imagen
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .aspectRatio(0.6f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = book.id.toString()
                )

                Text(
                    text = book.title,
                    style = Typography.titleLarge
                )
                Text(
                    text = book.author,
                    style = Typography.titleMedium
                )
                Text(
                    text = book.publication,
                    style = Typography.titleMedium
                )
            }
        }
    }

}

