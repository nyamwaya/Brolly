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


class DayListAdapter : RecyclerView.Adapter<DayListAdapter.ViewHolder>() {

    private val dailyWeatherList: ArrayList<com.example.aleck.brolly.model.daily.Data> = ArrayList()

    private val hourlyWeatherList: ArrayList<com.example.aleck.brolly.model.hourly.Data> = ArrayList()

    private var myTimeZone: String = String()

    private var isMetricMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): DayListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_list_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size
    }

    override fun onBindViewHolder(viewholder: DayListAdapter.ViewHolder, position: Int) {

        val data = dailyWeatherList[position]
        val weeklyDateOfYear = StringFormatter.convertTimestampToDate(data.time.toLong(), myTimeZone)
        val dayOfWeek: String


        //set the day titl
        dayOfWeek = when (weeklyDateOfYear) {
            StringFormatter.getTodayDate() -> "Today"
            StringFormatter.getTomorrowDate() -> "Tomorrow"
            else -> StringFormatter.convertTimestampToDayOfTheWeek(data.time)
        }

        viewholder.bind(dayOfWeek)

        //create the hourly adapter
        val adapter = HourlyAdapter()
        viewholder.rv.adapter = adapter
        viewholder.rv.layoutManager = GridLayoutManager(viewholder.rv.context, 4)

        val formattedList = java.util.ArrayList<com.example.aleck.brolly.model.hourly.Data>()

        //create a new list who's items are equal to the current day
        for (t in hourlyWeatherList){
            val hourlyDateOfYear = StringFormatter.convertTimestampToDate(t.time.toLong(), myTimeZone)
            if (weeklyDateOfYear == hourlyDateOfYear) {
                formattedList.add(t)
            }
        }

        //pass data to the hourly recyclerview
        adapter.refreshList(formattedList, myTimeZone, isMetricMode)

    }

    fun refreshList(daily: List<Data>, data: List<com.example.aleck.brolly.model.hourly.Data>, timezone: String, metricMode: Boolean) {
        if (itemCount != 0) this.dailyWeatherList.clear()
        this.hourlyWeatherList.clear()

        isMetricMode = metricMode
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