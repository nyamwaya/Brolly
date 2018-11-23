package com.example.aleck.brolly.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.aleck.brolly.R
import com.example.aleck.brolly.model.daily.Data
import com.example.aleck.brolly.uitls.StringFormatter
import kotlin.collections.ArrayList

class DayListAdapter() : RecyclerView.Adapter<DayListAdapter.ViewHolder>() {

    private val dailyWeatherList: ArrayList<com.example.aleck.brolly.model.daily.Data> = ArrayList()

    private val hourlyWeatherList: ArrayList<com.example.aleck.brolly.model.hourly.Data> = ArrayList()

    private var myTimeZone: String = String()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): DayListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_list_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size + hourlyWeatherList.size
    }

    override fun onBindViewHolder(viewholder: DayListAdapter.ViewHolder, position: Int) {

        val dailyWeather = dailyWeatherList[position]
        val hourData = hourlyWeatherList[position]

        val dayOfWeek = StringFormatter.convertTimestampToDayOfTheWeek(dailyWeather.time)
        viewholder.bind(dayOfWeek)

        val adapter = HourlyAdapter()
        viewholder.rv.adapter = adapter
        viewholder.rv.layoutManager = GridLayoutManager(viewholder.rv.context, 4)

        val newList = java.util.ArrayList<com.example.aleck.brolly.model.hourly.Data>()
        val weeklyDateOfYear = StringFormatter.convertTimestampToDate(dailyWeather.time.toLong(), myTimeZone)

        if(hourlyWeatherList.size > 24){
            for (i in 0..24) {
                val hourlyDateOfYear = StringFormatter.convertTimestampToDate(hourlyWeatherList[i].time.toLong(), myTimeZone)
                if (weeklyDateOfYear == hourlyDateOfYear) {
                    newList.add(hourlyWeatherList[i])
                }
            }
        }

        adapter.refreshList(newList, myTimeZone)

    }

    fun refreshList(daily: List<Data>, data: List<com.example.aleck.brolly.model.hourly.Data>, timezone: String) {
        if (itemCount != 0) this.dailyWeatherList.clear()
        this.hourlyWeatherList.clear()

        myTimeZone = timezone
        dailyWeatherList.addAll(daily)
        hourlyWeatherList.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTitleView: TextView = itemView.findViewById(R.id.daytitle)
        val rv: RecyclerView = itemView.findViewById(R.id.hourly_list)

        fun bind(title: String) {
            dayTitleView.text = title

        }
    }

}