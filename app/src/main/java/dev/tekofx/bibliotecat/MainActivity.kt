package dev.tekofx.bibliotecat

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.tekofx.bibliotecat.call.BookService
import dev.tekofx.bibliotecat.call.LibraryService
import dev.tekofx.bibliotecat.navigation.NavigateDestinations
import dev.tekofx.bibliotecat.navigation.Navigation
import dev.tekofx.bibliotecat.navigation.showBottomAppBar
import dev.tekofx.bibliotecat.repository.BookRepository
import dev.tekofx.bibliotecat.repository.LibraryRepository
import dev.tekofx.bibliotecat.ui.IconResource
import dev.tekofx.bibliotecat.ui.components.BottomNavigationBar
import dev.tekofx.bibliotecat.ui.components.input.TextIconButton
import dev.tekofx.bibliotecat.ui.theme.MyApplicationTheme
import dev.tekofx.bibliotecat.ui.viewModels.book.BookViewModel
import dev.tekofx.bibliotecat.ui.viewModels.book.BookViewModelFactory
import dev.tekofx.bibliotecat.ui.viewModels.library.LibraryViewModel
import dev.tekofx.bibliotecat.ui.viewModels.library.LibraryViewModelFactory
import dev.tekofx.bibliotecat.ui.viewModels.preferences.Preferences
import dev.tekofx.bibliotecat.ui.viewModels.preferences.PreferencesViewModel
import dev.tekofx.bibliotecat.ui.viewModels.preferences.PreferencesViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val libraryViewModel = ViewModelProvider(
            this, LibraryViewModelFactory(
                LibraryRepository(
                    LibraryService.getInstance()
                )
            )
        )[LibraryViewModel::class.java]

        val bookViewModel = ViewModelProvider(
            this, BookViewModelFactory(BookRepository(BookService.getInstance()))
        )[BookViewModel::class.java]

        val preferencesViewModel = ViewModelProvider(
            this, PreferencesViewModelFactory(
                Preferences(
                    context = this
                )
            )
        )[PreferencesViewModel::class.java]

        installSplashScreen()
        setContent {
            val isDynamicColorEnabled by preferencesViewModel.isDynamicColorEnabled.collectAsState()
            MyApplicationTheme(
                dynamicColor = isDynamicColorEnabled
            ) {
                MainScreen(libraryViewModel, bookViewModel, preferencesViewModel)
                SetNavigationBarColor()
            }


        }


    }
}

@Composable
fun SetNavigationBarColor() {
    val color = MaterialTheme.colorScheme.surface
    val window = (LocalContext.current as Activity).window

    SideEffect {
        window.navigationBarColor = color.toArgb()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsInfoModalSheet(
    show: Boolean, onClose: () -> Unit, navController: NavHostController
) {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    fun close() {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onClose()
            }
        }
    }

    if (show) {
        ModalBottomSheet(
            onDismissRequest = {
                close()
            },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilledTonalButton(modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(NavigateDestinations.SETTINGS_ROUTE)
                    close()
                }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Settings, contentDescription = "SettingsScreen")
                        Text("Configuració")
                    }
                }
                FilledTonalButton(modifier = Modifier.fillMaxWidth(), onClick = {
                    navController.navigate(NavigateDestinations.ABOUT_ROUTE)
                    close()
                }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Info, contentDescription = "AboutScreen")
                        Text("Sobre")
                    }
                }
                TextIconButton(text = "Tanca",
                    startIcon = IconResource.fromImageVector(Icons.Outlined.Close),
                    onClick = { close() })

            }
        }
    }
}


@Composable
fun MainScreen(
    libraryViewModel: LibraryViewModel,
    bookViewModel: BookViewModel,
    preferencesViewModel: PreferencesViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var showModalSheet by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding(), bottomBar = {
        AnimatedVisibility(
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            visible = showBottomAppBar(currentRoute)
        ) {
            BottomNavigationBar(navHostController = navController,
                onMenuClick = { showModalSheet = true })
        }
    }) { padding ->
        SettingsInfoModalSheet(
            show = showModalSheet,
            onClose = { showModalSheet = false },
            navController = navController
        )

        Box(
            modifier = Modifier.padding(padding)
        ) {
            Navigation(navController, libraryViewModel, bookViewModel, preferencesViewModel)
        }

    }
}