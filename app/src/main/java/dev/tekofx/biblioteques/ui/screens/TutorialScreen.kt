package dev.tekofx.biblioteques.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.biblioteques.R
import dev.tekofx.biblioteques.RequestLocationPermissionUsingRememberLauncherForActivityResult
import dev.tekofx.biblioteques.navigation.NavigateDestinations
import dev.tekofx.biblioteques.ui.ColumnContainer
import dev.tekofx.biblioteques.ui.IconResource
import dev.tekofx.biblioteques.ui.components.input.TextIconButton
import dev.tekofx.biblioteques.ui.theme.Typography
import dev.tekofx.biblioteques.ui.viewModels.PreferencesViewModel

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TutorialScreen(
    navHostController: NavHostController,
    preferencesViewModel: PreferencesViewModel
) {
    var page by remember { mutableIntStateOf(0) }
    var previousPage by remember { mutableStateOf<Int?>(null) }


    val totalPages = 3
    Column {

        AnimatedContent(
            targetState = page,
            label = "",
            transitionSpec = {
                if (previousPage != null && page > previousPage!!) {

                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    ) togetherWith slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    )
                } else {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    ) togetherWith slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    )
                }
            }
        ) { targetPage ->
            when (targetPage) {
                0 -> Page1()
                1 -> Page2()
                2 -> Page3()
                3 -> Page4()
                else -> Page1() // Default fallback

            }
        }

        // Spacer with weight to push the next elements to the bottom
        Spacer(modifier = Modifier.weight(1f))

        Stepper(page, 4)

        Buttons(
            page = page,
            lastPage = totalPages,
            onFinishClicked = {
                preferencesViewModel.saveShowTutorial(false)
                navHostController.navigate(NavigateDestinations.WELCOME_SCREEN)

            },
            decreasePage = {
                previousPage = page
                page -= 1
            },
            increasePage = {
                previousPage = page
                page += 1
            }
        )


    }
}

@Composable
fun Stepper(actualPage: Int, totalPages: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (page in 0 until totalPages) {
            AnimatedContent(
                label = "stepper",
                targetState = page == actualPage,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(
                        animationSpec = tween(300)
                    )
                }) { isSelected ->
                val size = if (isSelected) 30.dp else 20.dp
                val color =
                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
                Icon(
                    modifier =
                    Modifier.size(size),
                    painter = IconResource.fromDrawableResource(R.drawable.circle)
                        .asPainterResource(),
                    tint = color,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun Page1() {
    ColumnContainer {
        Text(
            text = "Welcome to ${stringResource(R.string.app_name)}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = Typography.headlineLarge,
        )

        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier.size(300.dp),
            contentDescription = ""
        )
    }
}


@Composable
fun Page2() {
    ColumnContainer {
        Text(
            text = "Característiques",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = Typography.headlineLarge,
        )

        val bullet = "\u2022"
        val messages = listOf(
            "Veure biblioteques de la pronvíncia de Barcelona",
            "Filtrar biblioteques per Obert/Tancat, nom de la biblioteca o municipi",
            "Consulta informació sobre una biblioteca, com ara horari o ubicació",
            "Cerca llibres a Aladí amb una interfície d'usuari bonica",
        )


        messages.forEach {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "$bullet\t $it",
                textAlign = TextAlign.Justify,
            )
        }


    }
}

@Composable
fun Page3() {
    ColumnContainer {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Sobre aquesta aplicació",
            textAlign = TextAlign.Center,
            style = Typography.headlineLarge
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Aquesta app no és oficial de la Diputació de Barcelona"
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Aquesta aplicació és de codi obert, podeu consultar el codi aquí"
        )

        TextIconButton(
            text = "Github",
            onClick = {},
            icon = IconResource.fromDrawableResource(R.drawable.github_mark)
        )

    }
}

@Composable
fun Page4() {
    var show by remember { mutableStateOf(false) }
    ColumnContainer {
        Text(
            text = "Permisos",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = Typography.headlineLarge,
        )

        Text("Aquesta aplicació necessitarà accés a la ubicació si voleu trobar biblioteques a prop vostre")
        TextIconButton(
            text = "Mostra la finestra emergent de permisos",
            icon = IconResource.fromImageVector(Icons.Outlined.LocationOn),
            onClick = { show = !show }
        )

        if (show) {
            RequestLocationPermissionUsingRememberLauncherForActivityResult(
                onPermissionGranted = {},
                onPermissionDenied = {}
            )
        }
    }
}


@Composable
fun Buttons(
    page: Int,
    lastPage: Int,
    onFinishClicked: () -> Unit,
    increasePage: () -> Unit,
    decreasePage: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = 0.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {

        AnimatedVisibility(
            visible = page != 0,
            enter = scaleIn() + expandHorizontally(),
            exit = scaleOut() + shrinkHorizontally()
        ) {
            TextIconButton(
                text = "Enrere",
                icon = IconResource.fromImageVector(Icons.AutoMirrored.Outlined.KeyboardArrowLeft),
                enabled = page > 0,
                onClick = decreasePage
            )
        }
        AnimatedVisibility(
            visible = page != 0,
            enter = scaleIn() + expandHorizontally(),
            exit = scaleOut() + shrinkHorizontally()
        ) {
            Spacer(modifier = Modifier.width(40.dp))
        }


        AnimatedContent(
            targetState = page,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(
                    animationSpec = tween(
                        300
                    )
                )
            },
            label = ""
        ) { targetState ->
            if (targetState == lastPage) {
                TextIconButton(
                    text = "Acabar",
                    icon = IconResource.fromImageVector(Icons.Outlined.Check),
                    onClick = onFinishClicked
                )
            } else {
                TextIconButton(
                    text = "Endavant",
                    icon = IconResource.fromImageVector(Icons.AutoMirrored.Outlined.KeyboardArrowRight),
                    onClick = increasePage
                )
            }
        }

    }
}

