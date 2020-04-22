package com.example.covid19_tracker.ui.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19_tracker.R
import com.example.covid19_tracker.model.Country

  class BaseRecyclerViewAdapter(private var dataList: MutableList<Country>, private val context: Context?, private val listener: OnEvent) : RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>(),
    Filterable {
      var dataCopy = mutableListOf<Country>()
      var subscribeFlag = false
      var subCountry : String? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val pref = context?.getSharedPreferences("sub_country", Context.MODE_PRIVATE)
        subCountry = pref?.getString("country" , "n/a")
        if(!subCountry.equals("n/a")){
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

        if (subscribeFlag ){
            if (!subCountry.equals(covidModel.country_name,ignoreCase = true)){
                holder.pinImageView.visibility = INVISIBLE
            }
        }else{
            holder.pinImageView.visibility = VISIBLE
        }

        holder.pinImageView.setOnClickListener(View.OnClickListener {
            val pref = context?.getSharedPreferences("sub_country", Context.MODE_PRIVATE)
            val editor = pref?.edit()
            if (subscribeFlag){
                subscribeFlag = false
                editor?.clear()
                editor?.apply()
            }else {
                editor?.putString("country", covidModel.country_name)
                editor?.apply()
                dataList.removeAt(position)
                dataList.add(0,covidModel)
            }

            notifyDataSetChanged()
        })
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


        init {
            rowNumberTextView = itemLayoutView.findViewById(R.id.rowNumber)
            countryTextView = itemLayoutView.findViewById(R.id.countryName)
            casesTextView = itemLayoutView.findViewById(R.id.confirmedCount)
            deathsTextView = itemLayoutView.findViewById(R.id.deathCount)
            recoveredTextView = itemLayoutView.findViewById(R.id.recoveredCount)
            newcasesTextView = itemLayoutView.findViewById(R.id.newCasesCount)
            pinImageView = itemLayoutView.findViewById(R.id.pinImageView)



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
