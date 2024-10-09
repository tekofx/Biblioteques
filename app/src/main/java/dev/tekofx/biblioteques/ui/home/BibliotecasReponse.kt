package dev.tekofx.biblioteques.ui.home

import com.google.gson.annotations.SerializedName

data class BibliotecasReponse (
    @SerializedName("status") var status: String,
    @SerializedName("elements") var elements: ArrayList<Biblioteca> = arrayListOf()
)