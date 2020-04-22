package com.example.covid19_tracker.ui.fragments

import android.content.Context
import android.content.Intent
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19_tracker.R
import com.example.covid19_tracker.model.Country
import com.example.covid19_tracker.ui.activities.DetailsActivity

class BaseRecyclerViewAdapter(
    private var dataList: MutableList<Country>,
    private val context: Context?,
    private val listener: OnEvent
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>(),
    Filterable {
    var dataCopy = mutableListOf<Country>()
    var subscribeFlag = false
    var subCountry: String? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val pref = context?.getSharedPreferences("sub_country", Context.MODE_PRIVATE)
        subCountry = pref?.getString("country", "n/a")
        if (!subCountry.equals("n/a")) {
            subscribeFlag = true
        }
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val covidModel = dataList.get(position)
        holder.rowNumberTextView.text = (holder.adapterPosition + 1).toString()
        holder.countryTextView.text = covidModel.country_name
        holder.casesTextView.text = covidModel.cases
        holder.deathsTextView.text = covidModel.deaths
        holder.recoveredTextView.text = covidModel.total_recovered
        holder.newcasesTextView.text = covidModel.new_cases

        holder.cardView.setOnClickListener {
            gotoDetailsActivity(covidModel)
        }

        if (subscribeFlag) {
            if (!subCountry.equals(covidModel.country_name, ignoreCase = true)) {
                holder.pinImageView.visibility = INVISIBLE
            }
        } else {
            holder.pinImageView.visibility = VISIBLE
        }

        holder.pinImageView.setOnClickListener(View.OnClickListener {
            val pref = context?.getSharedPreferences("sub_country", Context.MODE_PRIVATE)
            val editor = pref?.edit()
            if (subscribeFlag) {
                subscribeFlag = false
                editor?.clear()
                editor?.apply()
            } else {
                editor?.putString("country", covidModel.country_name)
                editor?.apply()
                dataList.removeAt(position)
                dataList.add(0, covidModel)
            }

            notifyDataSetChanged()
        })
    }

    private fun gotoDetailsActivity(covidModel: Country) {
        var intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("country_name", covidModel.country_name)
        intent.putExtra("active_cases", covidModel.active_cases)
        intent.putExtra("cases", covidModel.cases)
        intent.putExtra("deaths", covidModel.deaths)
        intent.putExtra("new_cases", covidModel.new_cases)
        intent.putExtra("new_deaths", covidModel.new_deaths)
        intent.putExtra("total_recovered", covidModel.total_recovered)
        context?.startActivity(intent)
    }


    internal fun setCovid(dataLists: List<Country>) {
        dataList.addAll(dataLists)
        dataCopy.addAll(dataLists)
        notifyDataSetChanged()
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var rowNumberTextView: TextView
        var countryTextView: TextView
        var casesTextView: TextView
        var deathsTextView: TextView
        var recoveredTextView: TextView
        var newcasesTextView: TextView
        var pinImageView: ImageView
        var cardView: ConstraintLayout


        init {
            rowNumberTextView = itemLayoutView.findViewById(R.id.rowNumber)
            countryTextView = itemLayoutView.findViewById(R.id.countryName)
            casesTextView = itemLayoutView.findViewById(R.id.confirmedCount)
            deathsTextView = itemLayoutView.findViewById(R.id.deathCount)
            recoveredTextView = itemLayoutView.findViewById(R.id.recoveredCount)
            newcasesTextView = itemLayoutView.findViewById(R.id.newCasesCount)
            pinImageView = itemLayoutView.findViewById(R.id.pinImageView)
            cardView = itemLayoutView.findViewById(R.id.cardRootView)
        }
    }

    fun clear() {
        dataList.clear()
        dataCopy.clear()
    }

    fun search(query: String) {
        dataList.clear()
        this.dataList.addAll(dataCopy)

        if (query.isNotEmpty()) {
            var index = 0

            while (index < dataList.size) {
                val item = dataList[index]
                val text = item.country_name

                if (text.toLowerCase().contains(query.toLowerCase()).not()) {
                    dataList.removeAt(index)
                    index--
                }
                index++
            }
            listener.logEvent(query)
        }

        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface OnEvent {
        fun logEvent(query: String)
    }
}
