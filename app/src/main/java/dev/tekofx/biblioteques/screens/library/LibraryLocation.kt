package dev.tekofx.biblioteques.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tekofx.biblioteques.model.Library
import dev.tekofx.biblioteques.model.LibraryDummy
import dev.tekofx.biblioteques.ui.IconResource

@Composable
fun LibraryLocation(library: Library) {
    Surface(
        tonalElevation = 20.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier.width(40.dp).height(40.dp),
                painter = IconResource.fromImageVector(Icons.Outlined.LocationOn)
                    .asPainterResource(),
                contentDescription = "Location"
            )
            Text(text = library.fullAddress)
        }
    }
}

@Preview
@Composable
fun LibraryContactPreview() {
    LibraryLocation(LibraryDummy)
}