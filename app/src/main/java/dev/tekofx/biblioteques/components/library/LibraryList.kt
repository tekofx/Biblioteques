package dev.tekofx.biblioteques.components.library

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dev.tekofx.biblioteques.call.LibraryService
import dev.tekofx.biblioteques.repository.LibraryRepository
import dev.tekofx.biblioteques.ui.viewModels.library.LibraryViewModel
import dev.tekofx.biblioteques.ui.viewModels.library.LibraryViewModelFactory
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryList(
    navHostController: NavHostController,
    libraryViewModel: LibraryViewModel = viewModel(
        factory = LibraryViewModelFactory(
            LibraryRepository(LibraryService.getInstance())
        )
    )
) {
    val libraries by libraryViewModel.libraries.observeAsState(emptyList())
    val listState = rememberLazyListState()
    val isLoading by libraryViewModel.isLoading.observeAsState(false)
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showOnlyOpen by remember { mutableStateOf(false) }
    val density = LocalDensity.current



    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Cercar") },
                icon = { Icon(Icons.Filled.Search, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                }
            )
        }
    ) {
        // Screen content

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (libraries.isEmpty()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(20.dp),
                    text = "No hi ha llibreries que coincideixin amb els filtres :(",
                    lineHeight = 40.sp,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
            AnimatedVisibility(
                visible = libraries.isNotEmpty(),
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp)
                ) {
                    items(libraries) { library ->
                        LibraryCard(navHostController, library)
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        )
                    }
                }
            }
        }


        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {


                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        value = libraryViewModel.queryText,
                        onValueChange = { newText ->
                            libraryViewModel.onSearchTextChanged(newText)
                        },
                        singleLine = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text("Nom Biblioteca") }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = "Obert ara")
                        Switch(
                            checked = showOnlyOpen,
                            onCheckedChange = {
                                showOnlyOpen = it
                                libraryViewModel.filterOpen(it)
                            }
                        )
                    }


                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }) {
                        Text("Tanca")
                    }
                }

            }
        }
    }

}

//@Preview
//@Composable
//fun LibraryListPreview() {
//    LibraryList()
//}