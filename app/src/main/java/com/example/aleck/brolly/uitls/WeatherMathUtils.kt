package com.example.aleck.brolly.uitls


object WeatherMathUtils {

    fun convertFahrenheitToCelsius(temperatureFahrenheit: Double?): Double? =
        if (temperatureFahrenheit != null)
            ((temperatureFahrenheit - 32) * 5) / 9
        else null
}