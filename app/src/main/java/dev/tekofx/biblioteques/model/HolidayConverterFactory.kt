package dev.tekofx.biblioteques.model

import dev.tekofx.biblioteques.dto.HolidayResponse
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HolidayConverterFactory : Converter.Factory() {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>, retrofit: Retrofit
    ): Converter<ResponseBody, HolidayResponse> {
        return Converter { responseBody ->
            responseBody.use { body ->

                val data = body.string()
                val jsonArray = JSONArray(data)
                var holidayList: List<HolidayDay> = emptyList()

                if (jsonArray.getJSONObject(0).has("any_calendari")) {
                    holidayList = constructLocalHolidayDays(jsonArray)
                } else {
                    holidayList = constructGeneralCataloniaHolidayDays(jsonArray)
                }

                HolidayResponse(holidayList)
            }
        }
    }

    private fun constructGeneralCataloniaHolidayDays(jsonArray: JSONArray): List<HolidayDay> {
        val localHolidayList = mutableListOf<HolidayDay>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val year = jsonObject.getInt("any")
            val localDateTime = LocalDateTime.parse(jsonObject.getString("data"), formatter)
            val localDate = localDateTime.toLocalDate()
            val holidayName = jsonObject.getString("nom_del_festiu")

            val holidayDay = HolidayDay(
                year = year,
                date = localDate,
                place = "Catalunya",
                holiday = holidayName,
                postalCode = "08"
            )
            localHolidayList.add(holidayDay)
        }
        return localHolidayList
    }

    private fun constructLocalHolidayDays(jsonArray: JSONArray): List<HolidayDay> {
        val localHolidayList = mutableListOf<HolidayDay>()
        for (i in 0 until jsonArray.length()) {

            val jsonObject = jsonArray.getJSONObject(i)


            val year = jsonObject.getInt("any_calendari")
            val localDateTime = LocalDateTime.parse(jsonObject.getString("data"), formatter)
            val localDate = localDateTime.toLocalDate()
            val place = jsonObject.getString("ajuntament_o_nucli_municipal")
            val postalCode = jsonObject.getString("codi_municipi_ine")
            val holiday = jsonObject.getString("festiu")

            val holidayDay = HolidayDay(
                year = year,
                date = localDate,
                place = place,
                holiday = holiday,
                postalCode = postalCode
            )
            localHolidayList.add(holidayDay)
        }
        return localHolidayList
    }


    companion object {
        fun create(): HolidayConverterFactory {
            return HolidayConverterFactory()
        }
    }
}