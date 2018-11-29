package com.example.aleck.brolly.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.aleck.brolly.R
import com.example.aleck.brolly.model.hourly.Data
import com.example.aleck.brolly.networking.IconApi
import com.example.aleck.brolly.uitls.StringFormatter
import com.example.aleck.brolly.uitls.WeatherMathUtils
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    private val hourlyWeatherList: ArrayList<Data> = ArrayList()
    private var myTimeZone: String = String()
    private var isMetricMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): HourlyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hourlyWeatherList.size
    }


    override fun onBindViewHolder(viewholder: HourlyAdapter.ViewHolder, position: Int) {
        val data = hourlyWeatherList[position]

        //get the min temp of the day
        val minTemp = hourlyWeatherList.minBy { it.temperature }
        //get the index of the min temp
        val minTempIndex = hourlyWeatherList.indexOfFirst { it.temperature.toInt() == minTemp?.temperature?.toInt() }

        val maxTemp = hourlyWeatherList.maxBy { it.temperature }
        val maxTempIndex = hourlyWeatherList.indexOfFirst { it.temperature.toInt() == maxTemp?.temperature?.toInt() }

        viewholder.bind(data, position, minTempIndex, maxTempIndex)
    }

    //get info from day list adapter
    fun refreshList(hourlyData: ArrayList<Data>, timezone: String, metricMode: Boolean) {
        if (itemCount != 0) this.hourlyWeatherList.clear()
        this.hourlyWeatherList.clear()

        isMetricMode = metricMode
        myTimeZone = timezone
        hourlyWeatherList.addAll(hourlyData)

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hourOfDay: TextView = itemView.findViewById(R.id.time_of_day)
        private val tempOfDay: TextView = itemView.findViewById(R.id.temperature_content)
        private val weatherIcon: ImageView = itemView.findViewById(R.id.weather_icon)

        fun bind(data: Data, position: Int, minTempIndex: Int, maxTempIndex: Int) {

            //logic to set the color to the first instance of the highest and lowest temp of the days
            // when the minTempIndex is equal to the position
            when {
                maxTempIndex == position -> {
                    hourOfDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.weather_warm))
                    weatherIcon.setColorFilter(ContextCompat.getColor(itemView.context, R.color.weather_warm))
                    tempOfDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.weather_warm))
                }
                minTempIndex == position -> {
                    hourOfDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.weather_cool))
                    weatherIcon.setColorFilter(ContextCompat.getColor(itemView.context, R.color.weather_cool))
                    tempOfDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.weather_cool))
                }
                else -> weatherIcon.setColorFilter(ContextCompat.getColor(itemView.context, android.R.color.black))
            }

            //if it's metric mode, change the weather to celsius
            if (isMetricMode) {
                tempOfDay.text = WeatherMathUtils.convertFahrenheitToCelsius(data.temperature)?.roundToInt().toString()

            } else {
                tempOfDay.text = WeatherMathUtils.addDegreeSign(data.temperature.toInt().toString())
            }

            hourOfDay.text = StringFormatter.convertFrom24To12(data.time.toLong(), "America/Chicago")
            weatherIcon.imageAlpha

            //Get weather icons from the api
            //also find a placeholder when the network is down or icon api is down
            Picasso.get()
                .load(IconApi.getUrlForIcon(data.icon, false))
                //  .placeholder(R.drawable.user_placeholder)
                //      .error(R.drawable.user_placeholder_error)
                .into(weatherIcon)
        }
    }

}