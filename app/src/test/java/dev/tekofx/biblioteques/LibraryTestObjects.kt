package dev.tekofx.biblioteques

import dev.tekofx.biblioteques.model.library.DayTimeTable
import dev.tekofx.biblioteques.model.library.Library
import dev.tekofx.biblioteques.model.library.Season
import dev.tekofx.biblioteques.model.library.SeasonTimeTable
import dev.tekofx.biblioteques.model.library.TimeInterval
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

var firstDayOfWinter = LocalDate.of(2024, 9, 2)
var lastDayOfWinter = LocalDate.of(2025, 6, 22)
var firstDayOfSummer = LocalDate.of(2025, 6, 22)
var lastDayOfSummer = LocalDate.of(2025, 9, 2)

var mondayWinterTimetable = DayTimeTable(
    timeIntervals = listOf(
        TimeInterval(
            from = LocalTime.of(15, 30),
            to = LocalTime.of(20, 30)
        ),
    )
)

var tuesdayWinterTimetable = DayTimeTable(
    timeIntervals = listOf(
        TimeInterval(
            from = LocalTime.of(10, 0),
            to = LocalTime.of(13, 30)
        ),
        TimeInterval(
            from = LocalTime.of(15, 30),
            to = LocalTime.of(20, 30)
        ),
    )
)

var wednesdayWinterTimetable = DayTimeTable(
    listOf(
        TimeInterval(
            from = LocalTime.of(15, 30),
            to = LocalTime.of(20, 30),
        )
    )
)

var thursdayWinterTimetable = DayTimeTable(
    listOf(

        TimeInterval(
            from = LocalTime.of(10, 0),
            to = LocalTime.of(13, 30),
        ),
        TimeInterval(
            from = LocalTime.of(15, 30),
            to = LocalTime.of(20, 30),
        )
    )

)

var fridayWinterTimetable = DayTimeTable(
    listOf(

        TimeInterval(
            from = LocalTime.of(15, 30),
            to = LocalTime.of(20, 30),
        ),
    )
)

var saturdayWinterTimetable = DayTimeTable(
    listOf(

        TimeInterval(
            from = LocalTime.of(10, 0),
            to = LocalTime.of(13, 30),
        )
    )
)

var sundayWinterTimetable = DayTimeTable(
    listOf()
)


var mondaySummerTimetable = DayTimeTable(
    timeIntervals = listOf(


        TimeInterval(
            from = LocalTime.of(15, 30),
            to = LocalTime.of(20, 30)
        )

    )
)

var tuesdaySummerTimetable = DayTimeTable(
    timeIntervals = listOf(
        TimeInterval(
            from = LocalTime.of(15, 30),
            to = LocalTime.of(20, 30),

            ),
    )

)

var wednesdaySummerTimetable = DayTimeTable(
    listOf(
        TimeInterval(
            from = LocalTime.of(15, 30),
            to = LocalTime.of(20, 30),
        )
    )
)

var thursdaySummerTimetable = DayTimeTable(
    listOf(

        TimeInterval(
            from = LocalTime.of(10, 0),
            to = LocalTime.of(14, 0),
        ),

        TimeInterval(
            from = LocalTime.of(15, 30),
            to = LocalTime.of(20, 30),
        ),
    )

)

var fridaySummerTimetable = DayTimeTable(
    listOf(

        TimeInterval(
            from = LocalTime.of(10, 0),
            to = LocalTime.of(14, 0),
        ),
    )
)

var saturdaySummerTimetable = DayTimeTable(
    listOf()
)

var sundaySummerTimetable = DayTimeTable(
    listOf()
)

var winterTimetable = SeasonTimeTable(
    start = firstDayOfWinter,
    end = lastDayOfWinter,
    dayTimetables = mapOf(
        DayOfWeek.MONDAY to mondayWinterTimetable,
        DayOfWeek.TUESDAY to tuesdayWinterTimetable,
        DayOfWeek.WEDNESDAY to wednesdayWinterTimetable,
        DayOfWeek.THURSDAY to thursdayWinterTimetable,
        DayOfWeek.FRIDAY to fridayWinterTimetable,
        DayOfWeek.SATURDAY to saturdayWinterTimetable,
        DayOfWeek.SUNDAY to sundayWinterTimetable
    ),
    season = Season.WINTER,
    observation = ""
)

var summerTimetable = SeasonTimeTable(
    start = firstDayOfSummer,
    end = lastDayOfSummer,
    dayTimetables = mapOf(
        DayOfWeek.MONDAY to mondaySummerTimetable,
        DayOfWeek.TUESDAY to tuesdaySummerTimetable,
        DayOfWeek.WEDNESDAY to wednesdaySummerTimetable,
        DayOfWeek.THURSDAY to thursdaySummerTimetable,
        DayOfWeek.FRIDAY to fridaySummerTimetable,
        DayOfWeek.SATURDAY to saturdaySummerTimetable,
        DayOfWeek.SUNDAY to sundaySummerTimetable
    ),
    season = Season.SUMMER,
    observation = ""
)


var LibraryAbrera = Library(

    id = "biblioteca2058919",
    adrecaNom = "Biblioteca Josep Roca i Bros",
    description = " Biblioteca Josep Roca i Bros. Abrera",
    municipality = "Abrera",
    address = "Carrer Federico García Lorca, 17 08630 Abrera",
    bibliotecaVirtualUrl = "/abrera-biblioteca-josep-roca-i-bros",
    emails = listOf("b.abrera.jrb@diba.cat"),
    phones = listOf("937 700 881"),
    webUrl = "https://www.biblioabrera.cat",
    image = "https://bibliotecavirtual.diba.cat/documents/346453/0/Abrera_edifici_exterior+vitrall.jpg/b5b6ae1d-a271-422e-8fe3-bf2a993a9713?t=1327512463534",
    summerSeasonTimeTable = summerTimetable,
    winterTimetable = winterTimetable,
    location = listOf(123.0, 12.2),
    postalCode = "09012"
)