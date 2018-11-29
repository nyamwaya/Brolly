package com.example.aleck.brolly

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.example.aleck.brolly.adapter.DayListAdapter
import com.example.aleck.brolly.application.WeatherApp
import com.example.aleck.brolly.model.WeatherResponse
import com.example.aleck.brolly.presenter.WeatherPresenter
import com.example.aleck.brolly.presenter.dependencies.DaggerRepoListComponent
import com.example.aleck.brolly.presenter.dependencies.WeatherModule
import com.example.aleck.brolly.uitls.StateCodeUtils
import com.example.aleck.brolly.uitls.WeatherMathUtils
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.math.roundToInt
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar


/**
 * This activity contains the weather view. It is designed to be
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

        openSettings()

        val recyclerView = findViewById<RecyclerView>(R.id.day_list)
        dayListAdapter = DayListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dayListAdapter
    }

    override fun onResume() {
        super.onResume()
        /**
         * Here we have a problem, we make way too many requests to the api,
         * this is very expensive on our part, and on the user part (data plan)
         * or if they have slow data.
         *
         * In the next update, use Room to store that data and simply check if there is a zipcode
         * and if the zipcode is equal to the new old zipcode get saved data, if not make a network request
         */
        getUserLocation()
    }

    private fun isMetricMode(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        val mKey = resources.getString(R.string.pref_units_key)
        val unit = WeatherMathUtils.readFromSharedPref(sharedPreferences, mKey, "english")
        if (unit.equals("metric")) {
            return true
        }
        return false
    }

    /**
     * This method checks to see if we have a zipCode, if not, launch the settings activiy
     * if we do have a zip code, get the generate a latitude and longitude from it
     *
     * NOTE: The zipcode is stored in the getDefaultSharedPreferences
     *
     * WARNING: Sometimes the app crashes due Android Geocode api on some devices
     * consider suing the Google Maps Geocodeing API instead.
     * https://developers.google.com/maps/documentation/geocoding/start
     */
    private fun getUserLocation() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        val mKey = resources.getString(R.string.pref_location_key)
        val zipCode = WeatherMathUtils.readFromSharedPref(sharedPreferences, mKey, "")

        if (zipCode == null || zipCode.isEmpty()) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        } else {
            val lat = WeatherMathUtils.convertZipToLat(this, zipCode)
            val long = WeatherMathUtils.convertZipToLong(this, zipCode)
            weatherPresenter.requestWeather(lat, long)
        }
    }

    override fun onWeatherDataResponse(data: WeatherResponse) {
        //pass necessary data to the DayListAdapter
        dayListAdapter.refreshList(data.daily.data, data.hourly.data, data.timezone, isMetricMode())

        //build the current conditions view at
        buildCurrentConditions(data)
    }

    private fun openSettings() {
        val settings: ImageView = SettingsCog
        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buildCurrentConditions(data: WeatherResponse) {
        //prepare the views
        val mCurrentTemp: TextView = CurrentTemperatureLabel
        val mCondition: TextView = ConditionsLabel
        val mLocation: TextView = CityStateLabel
        val mCurrentConditions = CurrentConditionView
        val baseTemp = 60

        //get the city name and state
        val cityName = WeatherMathUtils.getCity(this, data.latitude, data.longitude)
        val mState = WeatherMathUtils.getState(this, data.latitude, data.longitude)

        //change temp to metric mode if its metric mode is true
        if (isMetricMode()) {
            val tempMetric =
                WeatherMathUtils.convertFahrenheitToCelsius(data.currently.temperature)?.roundToInt().toString()
            mCurrentTemp.text = WeatherMathUtils.addDegreeSign(tempMetric)
        } else {
            mCurrentTemp.text = WeatherMathUtils.addDegreeSign(data.currently.temperature.roundToInt().toString())
        }

        //set the datat to the location and the condition
        mCondition.text = data.currently.summary
        mLocation.text = cityName + ", " + StateCodeUtils.getStateCode(mState)


        //change the current condition view to appropriate colors
        if (data.currently.temperature.roundToInt() >= baseTemp) {
            mCurrentConditions.setBackgroundColor(resources.getColor(R.color.weather_warm))
        } else {
            mCurrentConditions.setBackgroundColor(resources.getColor(R.color.weather_cool))
        }
    }

    override fun onErrorResponse(errorMsg: String) {
        //Do some error handling
        val snackbar: Snackbar = Snackbar.make(
            main_content,
            "The given zipcode is invalid, please change the ZipCode under settings: Error $errorMsg ",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("Dismiss") {
            snackbar.dismiss()
        }

        snackbar.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        weatherPresenter.onDestroy()
    }
}
