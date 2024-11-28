package dev.tekofx.biblioteques.navigation

object NavigateDestinations {
    const val WELCOME_SCREEN = "WelcomeScreen"
    const val TUTORIAL_SCREEN = "TutorialScreen"
    const val LIBRARIES_ROUTE = "LibrariesScreen"
    const val LIBRARY_DETAILS_ROUTE = "$LIBRARIES_ROUTE/library"
    const val BOOK_SEARCH_ROUTE = "BooksScreen"
    const val BOOK_RESULTS_ROUTE = "$BOOK_SEARCH_ROUTE/search"
    const val BOOK_DETAILS_ROUTE = "$BOOK_SEARCH_ROUTE/book"
}