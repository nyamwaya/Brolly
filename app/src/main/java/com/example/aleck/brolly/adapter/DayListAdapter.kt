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

class DayListAdapter() : RecyclerView.Adapter<DayListAdapter.ViewHolder>() {

    private val dailyWeatherList: ArrayList<com.example.aleck.brolly.model.daily.Data> = ArrayList()

    private val hourlyWeatherList: ArrayList<com.example.aleck.brolly.model.hourly.Data> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): DayListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_list_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size + hourlyWeatherList.size
    }

    override fun onBindViewHolder(viewholder: DayListAdapter.ViewHolder, position: Int) {

//            val hourData = hourlyWeatherList[position]

            //create gridlayout manager
            viewholder.rv.layoutManager = GridLayoutManager(viewholder.rv.context, 4)
            //Access RecyclerView Adapter and load the data
            val adapter = HourlyAdapter(hourlyWeatherList)
            viewholder.rv.adapter = adapter

            val dailyWeather = dailyWeatherList[position]
            val dayOfWeek = StringFormatter.convertTimestampToDayOfTheWeek(dailyWeather.time)
            viewholder.bind(dayOfWeek)




    }

    fun refreshList(daily: List<Data>, data: List<com.example.aleck.brolly.model.hourly.Data>){
        if (itemCount != 0) this.dailyWeatherList.clear()
        this.hourlyWeatherList.clear()

        dailyWeatherList.addAll(daily)
        hourlyWeatherList.addAll(data)
        notifyDataSetChanged()
    }

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private val dayTitleView: TextView = itemView.findViewById(R.id.daytitle)
       val rv: RecyclerView = itemView.findViewById(R.id.hourly_list)

       fun bind(title: String){
           dayTitleView.text = title

       }
    }

}