package dev.tekofx.biblioteques.ui.components.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import dev.tekofx.biblioteques.model.library.Library
import dev.tekofx.biblioteques.navigation.NavScreen
import dev.tekofx.biblioteques.ui.components.StatusBadge
import dev.tekofx.biblioteques.ui.theme.Typography
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun LibraryCard(navHostController: NavHostController, library: Library) {
    Surface(
        tonalElevation = 40.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = { navHostController.navigate("${NavScreen.LibrariesScreen.name}/${library.id}") }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = library.image, // Ajusta con tu imagen
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {

                    Text(
                        text = library.adrecaNom,
                        style = Typography.titleLarge
                    )
                    Text(
                        text = library.municipality,
                        style = Typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                StatusBadge(
                    library.getStatusColor(),
                    library.generateStateMessage(LocalDate.now(), LocalTime.now()),
                    Typography.bodyMedium
                )

            }
        }
    }

}


//@Preview
//@Composable
//fun LibraryCardPreview() {
//    LibraryItem(LibraryDummy)
//}