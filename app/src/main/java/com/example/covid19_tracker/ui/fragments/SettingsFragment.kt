package com.example.covid19_tracker.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.*
import androidx.work.*
import com.example.covid19_tracker.MyWorker
import com.example.covid19_tracker.R
import com.example.covid19_tracker.viewmodel.MainViewModel
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat() {
        protected lateinit var rootView: View
        private lateinit var viewModel: MainViewModel
        companion object {
            var TAG = SettingsFragment::class.java.simpleName
            const val ARG_POSITION: String = "positioin"

            fun newInstance(): SettingsFragment {
                var fragment =
                    SettingsFragment();
                val args = Bundle()
                args.putInt(ARG_POSITION, 1)
                fragment.arguments = args
                return fragment
            }
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences,rootKey)
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            // Get the switch preference
//            val switchNotify: SwitchPreferenceCompat? = findPreference("pref_key_check")
//
//            // Switch preference change listener
//            switchNotify?.setOnPreferenceChangeListener{ preference, newValue ->
//                if (newValue == true){
//                    Toast.makeText(activity,"enabled",Toast.LENGTH_LONG).show()
//                }else{
//                    Toast.makeText(activity,"disabled",Toast.LENGTH_LONG).show()
//                }
//
//                true
//            }

            val update = findPreference<ListPreference>("pref_update")
            update?.setOnPreferenceChangeListener { preference, newValue ->
                var index: Long? = null
                when(newValue){
                   getString(R.string.no_Alert)-> index = null
                    getString(R.string.one_hour) -> index = 1
                    getString(R.string.six_hours)-> index = 6
                    getString(R.string.twelve_hours)-> index = 12
                    getString(R.string.twenty_four_hours) -> index = 24
                    else -> index = null
                }

                    setBackGroundSync(index)
                true
            }


        }

    private fun setBackGroundSync(interval : Long?) {

        if (interval != null){

            //create constraints to attach it to the request
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            //create the request
            val myRequest = PeriodicWorkRequestBuilder<MyWorker>(repeatInterval = interval , repeatIntervalTimeUnit = TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(requireContext())
                .enqueueUniquePeriodicWork("update",
                ExistingPeriodicWorkPolicy.REPLACE,myRequest)
            Toast.makeText(
                activity,
                "you will be notified every $interval hour(s)",
                Toast.LENGTH_SHORT
            ).show()
            WorkManager.getInstance(requireContext())
                .getWorkInfosForUniqueWorkLiveData("update")
                .observe(this, Observer {
                        viewModel.getNewWorldData()
                        viewModel.getNewData()

                })
        }else{
            WorkManager.getInstance(requireContext()).cancelUniqueWork("update")
            Toast.makeText(activity,"you will not be notified",Toast.LENGTH_SHORT).show()
        }

    }
}