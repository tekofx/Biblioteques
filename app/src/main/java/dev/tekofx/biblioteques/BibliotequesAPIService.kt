package dev.tekofx.biblioteques

import dev.tekofx.biblioteques.ui.home.BibliotecasResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface BibliotequesAPIService {
    @GET
    suspend fun getBiblioteques(@Url url:String):Response<BibliotecasResponse>
}