package com.example.assignmentapi.network

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentapi.R
import com.example.assignmentapi.objects.Exclusion
import com.example.assignmentapi.objects.Facilities
import com.example.assignmentapi.objects.Facility
import com.example.assignmentapi.objects.Option


class RecyclerAdapter(
    private val facilityList: MutableList<Facility>,
    private val exclusionList: MutableList<MutableList<Exclusion>>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), AdapterView.OnItemSelectedListener {
    private val adapterList: MutableList<CustomAdapter> = mutableListOf()
    private val spinnerList: MutableList<Spinner> = mutableListOf()
    private var tempList: MutableList<Option> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_items, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recyclerModel = facilityList[position]
        holder.facilityName.text = recyclerModel.name
        val adapter = CustomAdapter(
            holder.context,
            R.layout.simple_spinner_item,
            recyclerModel.options.toMutableList() // make copy for array adapter so it doesn't delete original data
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        if (!adapterList.contains(adapter)) adapterList.add(adapter)
        holder.spinner.adapter = adapter
        holder.spinner.onItemSelectedListener = this
        if (!spinnerList.contains(holder.spinner)) spinnerList.add(holder.spinner)
    }

    override fun getItemCount(): Int {
        return facilityList.size
    }

    class ViewHolder(ItemView: View, val context: Context) : RecyclerView.ViewHolder(ItemView) {
        var facilityName: TextView = itemView.findViewById(R.id.facility_name)
        var spinner: Spinner = itemView.findViewById(R.id.spinner)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        for (x in adapterList.indexOf(p0?.adapter) until adapterList.size) {
            for (y in x + 1 until adapterList.size) {
                tempList.clear()
                tempList.add(Option(0, "Select option...", "question"))
                for (option in Facilities.facilities[y].options) {
                    tempList.add(Option(option.id, option.name, option.icon))
                }
                adapterList[y].clear()
                adapterList[y].addAll(tempList)
                exclusionList.map {
                    //check spinner that was clicked by user just now
                    if (p0?.selectedItem.toString() == it[0].options_id.toString()) {
                        tempList.map { option ->
                            if (option.id.toString() == it[1].options_id.toString()) {
                                adapterList[y].remove(option)
                            }
                        }
                    }
                    //check previous spinner selected item value
                    for (spinner in 0 until y) {
                        if (spinnerList[spinner].selectedItem.toString() == it[0].options_id.toString()) {
                            Facilities.facilities[it[1].facility_id - 1].options.map { option ->
                                if (option.id.toString() == it[1].options_id.toString()) {
                                    adapterList[it[1].facility_id - 1].remove(option)
                                }
                            }
                        }
                    }
                }
                spinnerList[y].setSelection(0)
            }
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        println("nothing selected")
    }
}