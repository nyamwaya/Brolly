package com.example.aleck.brolly.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.aleck.brolly.R
import com.example.aleck.brolly.model.hourly.Data
import com.example.aleck.brolly.uitls.StringFormatter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HourlyAdapter (private val hourlyWeatherList: ArrayList<Data>) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>(){

    //private val hourlyWeatherList: ArrayList<com.example.aleck.brolly.model.hourly.Data> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): HourlyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hourlyWeatherList.size
    }


    override fun onBindViewHolder(viewholder: HourlyAdapter.ViewHolder, position: Int) {
    //    Do Not Delete this
        val data = hourlyWeatherList[position]
        val hour = StringFormatter.convertTimestampToHourFormat(data.time.toLong(), "America/Chicago")
        val temp = data.temperature.toInt()
        viewholder.bind(hour, temp)

 //       val map = hourlyWeatherList.associateBy({StringFormatter.convertTimestampToHourFormat(it.time.toLong(), "America/Chicago")}, {it.temperature})

//        if (hourlyWeatherList.size > 24){
//            for (entry in map) {
//                val cal = Calendar.getInstance()
//                val currentHour = cal.get(Calendar.HOUR)
//                if (entry.key == currentHour.toString()){
//                    val hour = entry.key
//                    val temp = entry.value
//                    viewholder.bind(hour, temp.toInt())
//
//                }
//            }
//
//        }

//        val map = hourlyWeatherList.associateBy({
//            StringFormatter.convertTimestampToHourFormat(
//                it.time.toLong(),
//                "America/Chicago"
//            )
//        }, { it.temperature })
//
//        var map =
//
//        val cal = Calendar.getInstance()
//        val currentHour = cal.get(Calendar.HOUR_OF_DAY)
//
//        for (entry in map) {
//            if (entry.key.toInt() == currentHour) {
//                val hour = entry.key
//                val temp = entry.value
//                viewholder.bind(hour)
//            }
//        }


//        val map = hourlyWeatherList.associateBy({ StringFormatter.convertTimestampToHourFormat(it.time.toLong(), "America/Chicago")}, {it.temperature})
//
//        for (entry in map) {
//            if (entry.key == 12.00.toString()){
//                val hour = entry.key
//                val temp = entry.value
//                viewholder.bind(hour, temp.toInt())
//            }
//        }




//        hourlyWeatherList.forEach {
//            val cal = Calendar.getInstance()
//            val currentHour = cal.get(Calendar.HOUR)
//            val hour = it
//            val temp = it
//            viewholder.bind(hour)
//        }
//
//        val mm = hourlyWeatherStringFormatedHoursList



//        val temp = data[position].toString()
//
//        hourlyWeatherList.forEach {
//            val temp = it[position].ti .toInt()
//        }

//        for (i in 0..24){
//                if (StringFormatter.convertTimestampToHourFormat(data[i]. toLong(), "America/Chicago") < "00:))"){
//                val temp = data.temperature.toInt()
//                val hour = StringFormatter.convertTimestampToHourFormat(data.time.toLong(), "America/Chicago")
//                viewholder.bind(hour, temp)
//            }
//
//        }


        val calendar = Calendar.getInstance()
        val currentDay = "Wednesday"//calendar.get(Calendar.DAY_OF_WEEK) // 0 is Sunday, 1 is Monday, 2 is Tuesday...etc see java.util.Date api

//        val myNewList = hourlyWeatherList.filter {
//            val dateFormat = parseDate(it.time.toLong())
//
//            dateFormat == currentDay
//
//        }




//        val i = hourlyWeatherList.groupBy {it.time}
//        val temp = i[position]. .toInt()
//        val hour = StringFormatter.convertTimestampToHourFormat(i.time.toLong(), "America/Chicago")
//        viewholder.bind(hour, temp)

//        for (i in hourlyWeatherList){
//            //val data = hourlyWeatherList[i]
//
//            val dateFormat = parseDate(i.time.toLong())
//            if (dateFormat == currentDay){
//
//            }
//        }

 //       val t = myNewList[position]


       // val data = myNewList[position]
//        if (hourlyWeatherList.size >24){
//                for(t in hourlyWeatherList){
//                    val dateFormat = parseDate(t.time.toLong())
//                    if (dateFormat == currentDay){
//
//
//                    }
//                }
//
//            }

//        for (i in 0..24){
//
//
//        }

       // if (myNewList.size > 24){

        //}
     //   val data = hourlyWeatherList[position]
//
//
//        val mParsedData = parseDate(data.time.toLong())
//
//        if (mParsedData == currentDay){
//
//
//        }
//
//        val myList = ArrayList<Data>()
//
//        var t =myList[position]
//



        //        if (hourlyWeatherList.size > 24){
//            val parsedTime = hourlyWeatherList
//                .filter {data ->
//                    val DAY = "EEEE"
//                    val formatter = SimpleDateFormat (DAY, Locale.ENGLISH)
//
//                    val dateFormat = formatter.format(Date(hourlyData.time.toLong()))
//                }
//
//        }

        //val data =myList[position]






//        val filtered = hourlyWeatherList.filter {
//
//            val parsedTime = parseDate(it.time.toLong())
//
//            parsedTime == currentDay
//        }


      //  filtered[position]
      //  val data = filtered[position]



      //  val testTime = ArrayList<Data>()








//        if (hourlyWeatherList.size > 24) {
//
//        for (i in 0..24){
//            if (StringFormatter.convertTimestampToHourFormat(data.time.toLong(), "America/Chicago") < "00:))"){
//                val temp = data.temperature.toInt()
//                val hour = StringFormatter.convertTimestampToHourFormat(data.time.toLong(), "America/Chicago")
//                viewholder.bind(hour, temp)
//            }
//
//        }
//        }



    }

    private fun parseDate(time: Long): Any {
            val DAY = "EEEE"
            val formatter = SimpleDateFormat (DAY, Locale.ENGLISH)
            val dateFormat = formatter.format(Date(time))
            return dateFormat

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourOfDay: TextView = itemView.findViewById(R.id.time_of_day)
        private val tempOfDay: TextView = itemView.findViewById(R.id.temperature_content)
       // private val weatherIcon: ImageView = itemView.findViewById(R.id.weather_icon)

        fun bind(hour: String, temp: Int /* , icon: Int */){
            hourOfDay.text = hour
            tempOfDay.text = temp.toString()
        //    weatherIcon.imageAlpha = icon
        }
    }

}