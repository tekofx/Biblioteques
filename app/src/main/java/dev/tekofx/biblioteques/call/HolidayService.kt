package dev.tekofx.biblioteques.call

import dev.tekofx.biblioteques.dto.HolidayResponse
import dev.tekofx.biblioteques.model.HolidayConverterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url

interface HolidayService {


    @GET()
    fun getJson(@Url url: String): Call<HolidayResponse>


    companion object {

        private var holidayService: HolidayService? = null

        /**
         * Get an instance of the book service
         * (it will create one if needed)
         */
        fun getInstance(): HolidayService {

            if (holidayService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://analisi.transparenciacatalunya.cat/api/views/b4eh-r8up.json/")
                    .addConverterFactory(HolidayConverterFactory.create())
                    .build()
                holidayService = retrofit.create(HolidayService::class.java)
            }

            return holidayService!!
        }
    }
}