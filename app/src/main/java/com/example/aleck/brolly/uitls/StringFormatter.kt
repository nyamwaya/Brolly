package com.example.aleck.brolly.uitls

import java.text.SimpleDateFormat
import java.util.*


object StringFormatter {


    fun convertTimestampToDayOfTheWeek(timestamp: Int): String {
        val formatter = SimpleDateFormat("EEEE", Locale.ENGLISH)
        val dayName = formatter.format(Date(timestamp.toLong() * 1000))
        return dayName
    }

    fun convertTimestampToDate(timestamp: Long, timeZone: String): Any {
        val formatter = SimpleDateFormat("MM-dd-yyyy")
        formatter.timeZone = TimeZone.getTimeZone(timeZone)
        val date = Date(timestamp * 1000)

        return formatter.format(date)
    }

    fun convertTimestampToHourFormat(timestamp: Long, timeZone: String?): String {
        val HOUR_MINUTE = "HH:mm"
        val formatter = SimpleDateFormat(HOUR_MINUTE)
        formatter.timeZone = TimeZone.getTimeZone(timeZone)

        return formatter.format(Date(timestamp * 1000))
    }

    fun convertFrom24To12(timestamp: Long, timeZone: String?): String {
        val time24Stamp = convertTimestampToHourFormat(timestamp, timeZone)
        val formatter = SimpleDateFormat("HH:mm")

        val twelveHourFormatter = SimpleDateFormat("h:mm a")
        val twentyFourHour = formatter.parse(time24Stamp)

        return twelveHourFormatter.format(twentyFourHour)
    }

    fun getTomorrowDate(): String? {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)
        val formatter = SimpleDateFormat("MM-dd-yyyy")
        System.out.println(calendar.time)

        return formatter.format(calendar.time)
    }

    fun getTodayDate(): String? {
      return  SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance().time)
    }

}

