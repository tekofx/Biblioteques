package dev.tekofx.biblioteques.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Loader(
    isLoading: Boolean,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        if (isLoading) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "Obtenint biblioteques")
            }
        } else if (text.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(20.dp),
                text = text,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )
        }

    }
}