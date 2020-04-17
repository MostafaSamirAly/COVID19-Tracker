package com.example.covid19_tracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.*

    class SettingsFragment : PreferenceFragmentCompat() {
        protected lateinit var rootView: View

        companion object {
            var TAG = SettingsFragment::class.java.simpleName
            const val ARG_POSITION: String = "positioin"

            fun newInstance(): SettingsFragment {
                var fragment = SettingsFragment();
                val args = Bundle()
                args.putInt(ARG_POSITION, 1)
                fragment.arguments = args
                return fragment
            }
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences,rootKey)

            // Get the switch preference
            val switchNotify: SwitchPreferenceCompat? = findPreference("pref_key_check")

            // Switch preference change listener
            switchNotify?.setOnPreferenceChangeListener{ preference, newValue ->
                if (newValue == true){
                    Toast.makeText(activity,"enabled",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity,"disabled",Toast.LENGTH_LONG).show()
                }

                true
            }
        }
}