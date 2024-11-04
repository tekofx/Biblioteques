package dev.tekofx.biblioteques.model

class Book(
    val id: String,
    val title: String,
    val author: String,
    val image: String


) {


    override fun toString(): String {
        return "$id $author - $title"
    }
}