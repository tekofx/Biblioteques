package dev.tekofx.biblioteques.model.book

class BookDetails(
    val edition: String?,
    val description: String?,
    val synopsis: String?,
    val isbn: String?,
    val collections: List<String>,
    val topic: String?,
    val bookCopiesUrl: String?,
)