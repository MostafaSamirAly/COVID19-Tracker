package com.example.covid19_tracker

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19_tracker.model.Country

  class BaseRecyclerViewAdapter(private var dataList: List<Country>, private val context: Context?, private val listener: OnEvent) : RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>(),
    Filterable {
    lateinit var dataCopy: MutableList<Country>

     init {
        dataCopy = ArrayList(dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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

        holder.countryTextView.text = covidModel.country_name

        holder.casesTextView.text = covidModel.cases
        holder.deathsTextView.text = covidModel.deaths
        holder.recoveredTextView.text = covidModel.total_recovered
        holder.newcasesTextView.text = covidModel.new_cases
    }


    internal fun setCovid(dataLists: MutableList<Country>) {
        this.dataList = dataLists
        notifyDataSetChanged()
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        lateinit var countryTextView: TextView
        lateinit var casesTextView: TextView
        lateinit var deathsTextView: TextView
        lateinit var recoveredTextView: TextView
        lateinit var newcasesTextView: TextView

        init {
            countryTextView = itemLayoutView.findViewById(R.id.countryName)
            casesTextView = itemLayoutView.findViewById(R.id.confirmedCount)
            deathsTextView = itemLayoutView.findViewById(R.id.deathCount)
            recoveredTextView = itemLayoutView.findViewById(R.id.recoveredCount)
            newcasesTextView = itemLayoutView.findViewById(R.id.newCasesCount)


        }
    }

     fun clear() {
         dataCopy.clear()
         dataCopy.clear()
         notifyDataSetChanged()
     }

     fun search(query: String) {
         dataCopy.clear()
         this.dataCopy.addAll(dataList)

         if (query.isNotEmpty()) {
             var index = 0

             while (index < dataCopy.size) {
                 val item = dataCopy[index]
                 val text = item.country_name

                 if (text.toLowerCase().contains(query.toLowerCase()).not()) {
                     dataCopy.removeAt(index)
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
