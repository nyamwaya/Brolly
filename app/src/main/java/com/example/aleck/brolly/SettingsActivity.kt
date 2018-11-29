package com.example.aleck.brolly

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceManager
import android.view.MenuItem

/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 * See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
 * for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
 * for more information on developing a Settings UI.
 *
 * A lot of these methods are depricated, in a future update, use the preference fragment option
 */
class SettingsActivity : AppCompatPreferenceActivity(), Preference.OnPreferenceChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_general)

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)))
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)))

        setupActionBar()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun bindPreferenceSummaryToValue(preference: Preference) {
        //Set the listener to watch for value changes.
        preference.onPreferenceChangeListener = this

        //Trigger the listener immediately with the preference's current value
        onPreferenceChange(
            preference,
            PreferenceManager
                .getDefaultSharedPreferences(preference.context)
                .getString(preference.key, "")
        )

    }

    override fun onPreferenceChange(preference: Preference?, value: Any?): Boolean {
        val stringValue: String = value.toString()

        if (preference is ListPreference) {
            //For the list preference, look up the correct display value in
            //the preference's entries list (since they have separate labels/values)
            val listPreference: ListPreference = preference

            val prefIndex = listPreference.findIndexOfValue(stringValue)
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.entries[prefIndex])
            }
        } else run {
            // For other preferences, set the summary to the value's simple string representation.
            preference?.setSummary(stringValue)
        }
        return true
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun getParentActivityIntent(): Intent? {
        return super.getParentActivityIntent()!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
}
