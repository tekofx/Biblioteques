package dev.tekofx.biblioteques.repository

import android.util.Log
import dev.tekofx.biblioteques.call.HolidayService
import dev.tekofx.biblioteques.call.LibraryService
import dev.tekofx.biblioteques.dto.HolidayResponse
import dev.tekofx.biblioteques.dto.LibraryResponse
import dev.tekofx.biblioteques.model.holiday.Holiday
import dev.tekofx.biblioteques.model.library.Library
import retrofit2.Response
import retrofit2.awaitResponse
import java.net.UnknownHostException
import java.time.LocalDate

class LibraryRepository(
    private val libraryService: LibraryService,
    private val holidayService: HolidayService
) {
    suspend fun getLibraries(): LibraryResponse {
        try {
            val year = LocalDate.now().year

            val librariesResponse = libraryService.getLibraries().awaitResponse()
            val localHolidaysResponse = getLocalHolidaysResponse(year)
            val cataloniaHolidaysResponse = getCataloniaHolidaysResponse(year)

            val libraries = librariesResponse.body()?.elements ?: emptyList()

            // Add Barcelona to the list of municipalities
            val municipalities =
                librariesResponse.body()?.municipalities?.plus("Barcelona (Tots els districtes)")
                    ?: emptyList()

            val localHolidays = localHolidaysResponse?.body()?.body ?: emptyList()
            val cataloniaHolidays = cataloniaHolidaysResponse?.body()?.body ?: emptyList()

            val librariesWithHolidays =
                addHolidaysToLibraries(libraries, localHolidays, cataloniaHolidays)

            return LibraryResponse(librariesWithHolidays, municipalities)
        } catch (e: UnknownHostException) {
            Log.e("LibraryRepository", "Error getting libraries $e")
            throw e
        } catch (e: Exception) {
            Log.e("LibraryRepository", "Error getting libraries $e")
            throw e
        }
    }

    /**
     * Gets the local holiday days from the API
     * @param year: Int
     * @return Response<HolidayResponse>?
     */
    private suspend fun getLocalHolidaysResponse(year: Int): Response<HolidayResponse>? {
        val localHolidaysUrl =
            "https://analisi.transparenciacatalunya.cat/resource/b4eh-r8up.json?\$query=SELECT\n" +
                    "  `any_calendari`,\n" +
                    "  `data`,\n" +
                    "  `ajuntament_o_nucli_municipal`,\n" +
                    "  `codi_municipi_ine`,\n" +
                    "  `festiu`\n" +
                    "WHERE\n" +
                    "  (`any_calendari` = \"$year\")\n" +
                    "  AND (caseless_starts_with(`codi_municipi_ine`, \"08\")\n" +
                    "  AND (caseless_ne(`ajuntament_o_nucli_municipal`, \"null\")\n" +
                    "  AND caseless_ne(`ajuntament_o_nucli_municipal`, \"C. A. de Catalunya\"\n)))" +
                    "ORDER BY `data` ASC NULL LAST"

        val localHolidaysResponse = try {
            holidayService.getJson(localHolidaysUrl).awaitResponse()
        } catch (e: Exception) {
            Log.e("LibraryRepository", "Error getting local holiday days", e)
            null
        }

        Log.d(
            "LibraryRespository",
            "Got Local Holidays: ${localHolidaysResponse?.body()?.body?.size}"
        )

        return localHolidaysResponse
    }

    /**
     * Gets the Catalonia holiday days from the API
     * @param year: Int
     * @return Response<HolidayResponse>?
     */
    private suspend fun getCataloniaHolidaysResponse(year: Int): Response<HolidayResponse>? {
        val cataloniaHolidaysUrl =
            "https://analisi.transparenciacatalunya.cat/resource/8qnu-agns.json?\$query=SELECT `codi`, `any`, `data`, `nom_del_festiu` WHERE `any` IN (\"$year\")"

        val cataloniaHolidaysResponse = try {
            holidayService.getJson(cataloniaHolidaysUrl).awaitResponse()
        } catch (e: Exception) {
            Log.e("LibraryRepository", "Error getting catalonia holiday days", e)
            null
        }

        Log.d(
            "LibraryRespository",
            "Got Catalonia Holidays: ${cataloniaHolidaysResponse?.body()?.body?.size}"
        )

        return cataloniaHolidaysResponse
    }

    /**
     * Adds to a each [Library] in a List all the [Holiday] that affects that [Library]
     * @param libraries: List of [Library]
     * @param localHolidays: List of [Holiday]
     * @return List of [Library] with the [Holiday] of each [Library]
     */
    private fun addHolidaysToLibraries(
        libraries: List<Library>,
        localHolidays: List<Holiday>,
        cataloniaHolidays: List<Holiday>
    ): List<Library> {
        libraries.forEach { library: Library ->
            val holy = localHolidays.filter { holiday ->
                library.postalCode == holiday.postalCode
            }
            library.timetable?.holidays = holy.plus(cataloniaHolidays)
            library.updateLibraryStatus()
        }
        return libraries
    }
}