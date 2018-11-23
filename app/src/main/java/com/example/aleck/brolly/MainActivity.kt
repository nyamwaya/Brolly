package com.example.aleck.brolly

import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.aleck.brolly.adapter.DayListAdapter
import com.example.aleck.brolly.application.WeatherApp
import com.example.aleck.brolly.model.WeatherResponse
import com.example.aleck.brolly.model.hourly.Data
import com.example.aleck.brolly.presenter.WeatherPresenter
import com.example.aleck.brolly.presenter.dependencies.DaggerRepoListComponent
import com.example.aleck.brolly.presenter.dependencies.WeatherModule
import com.example.aleck.brolly.uitls.StringFormatter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

/**
 * This activity contains the weatjer view. It is designed to be
 * a dumb view devoid of any logic not pertaining to view manipulation.
 * For everything else dependencies are used
 */
class MainActivity : AppCompatActivity(), WeatherPresenter.WeatherView {

    @Inject
    lateinit var weatherPresenter: WeatherPresenter
    lateinit var dayListAdapter: DayListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //dagger is used to inject the presenter
        //this way dummy data can be supplied by a mock for testing
        DaggerRepoListComponent.builder()
            .appComponent((application as WeatherApp).appComponent)
            .weatherModule(WeatherModule(this))
            .build()
            .inject(this)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.day_list)
        dayListAdapter = DayListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dayListAdapter


    }

    override fun onResume() {
        super.onResume()
        weatherPresenter.requestWeather("45.182728", "-93.654464")
    }

    override fun onWeatherDataResponse(data: WeatherResponse) {
        dayListAdapter.refreshList(data.daily.data, data.hourly.data, data.timezone)
        setDayConditions(data)
        buildCurrentConditions(data)
    }


    private fun setDayConditions(data: WeatherResponse) {
        var newList = ArrayList<Data>()
        for (i in 0..24){
            val weeklyDateOfYear = StringFormatter.convertTimestampToDate(data.daily.data[i].time.toLong(), data.timezone)
            val hourlyDateOfYear = StringFormatter.convertTimestampToDate(data.hourly.data[i].time.toLong(), data.timezone)

            if(weeklyDateOfYear == hourlyDateOfYear){
                newList.add(data.hourly.data[i])

            }

            dayListAdapter.refreshList(data.daily.data, data.hourly.data, data.timezone)

        }




//
//        if(data.size > 25){
//            for (i in 0..24) {
//                val weeklyDateOfYear = StringFormatter.convertTimestampToDate(daily. .time.toLong(), timezone)
//
//                val hourlyDateOfYear = StringFormatter.convertTimestampToDate(hourlyWeatherList[i].time.toLong(), myTimeZone)
//
//                if (weeklyDateOfYear == hourlyDateOfYear) {
//                    newList.add(hourlyWeatherList[i])
//                }
//            }
//        }

    }


    private fun buildCurrentConditions(data: WeatherResponse) {
        val mCurrentTemp: TextView = findViewById(R.id.CurrentTemperatureLabel)
        val mCondition: TextView = findViewById(R.id.ConditionsLabel)
        val mLocation: TextView = findViewById(R.id.CityStateLabel)

        //move this to a helper class
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(data.latitude, data.longitude, 1)
        val cityName = addresses[0].locality



        mCurrentTemp.text = data.currently.temperature.roundToInt().toString()
        mCondition.text = data.currently.summary
        mLocation.text = cityName

    }

    private fun parseDate(time: Int): Any {
        val DAY = "EEEE"
        val formatter = SimpleDateFormat(DAY, Locale.ENGLISH)
        val dateFormat = formatter.format(Date(time.toLong()))
        return dateFormat

    }

    override fun onErrorResponse(errorMsg: String) {
        //Do some error handling
    }


    override fun onDestroy() {
        super.onDestroy()
        weatherPresenter.onDestroy()
    }
}
