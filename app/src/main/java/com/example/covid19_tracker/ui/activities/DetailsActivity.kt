package com.example.covid19_tracker.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covid19_tracker.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        countryNameDetail.text = intent.getStringExtra("country_name")
        confirmedCountDetail.text  = intent.getStringExtra("cases")
        deathCountDetail.text  = intent.getStringExtra("deaths")
        recoveredCountDetail.text  = intent.getStringExtra("total_recovered")
        newCasesCountDetail.text  = intent.getStringExtra("new_cases")
        activeCasescountDetail.text  = intent.getStringExtra("active_cases")
        newDeathsCountDetail.text  = intent.getStringExtra("new_deaths")
    }
}
