package com.example.aleck.brolly.uitls

import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import java.util.*


object WeatherMathUtils {

    fun convertFahrenheitToCelsius(temperatureFahrenheit: Double?): Double? {
        return if (temperatureFahrenheit != null) {
            ((temperatureFahrenheit - 32) * 5) / 9
        } else
            temperatureFahrenheit
    }

    fun addDegreeSign(temperature: String): String {
        return temperature + 0x00B0.toChar()
    }

    fun convertZipToLat(context: Context, zipCode: String): String {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(zipCode, 1)
        if (addresses != null && !addresses.isEmpty()) {
            val address = addresses[0]
            return String.format("%f", address.latitude)
        }
        return "Null"
    }

    fun convertZipToLong(context: Context, zipCode: String): String {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(zipCode, 1)
        if (addresses != null && !addresses.isEmpty()) {
            val address = addresses[0]
            return String.format("%f", address.longitude)
        }
        return "Null"
    }

    fun getState(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return addresses[0].adminArea
    }

    fun getCity(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return addresses[0].locality
    }

    fun saveToSharedPref(key: String, value: String, editor: SharedPreferences.Editor) {
        editor.putString(key, value)
        editor.apply()
    }

    fun readFromSharedPref(sharedPref: SharedPreferences, key: String, defaultValue: String): String? {
        return sharedPref.getString(key, defaultValue)
    }
}