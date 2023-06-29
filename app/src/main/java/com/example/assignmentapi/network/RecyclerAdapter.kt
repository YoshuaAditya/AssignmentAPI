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


class RecyclerAdapter(private val dataList: MutableList<Facility>, val exclusionList: MutableList<MutableList<Exclusion>>)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), AdapterView.OnItemSelectedListener {
    val adapterList:MutableList<CustomAdapter> = mutableListOf()
    var tempList:MutableList<Option> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_items, parent, false)
        return ViewHolder(view,parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recyclerModel = dataList[position]
//         Text set to the TextView widget
        holder.facilityName.text = recyclerModel.name
        val adapter = CustomAdapter(
            holder.context,R.layout.simple_spinner_item,recyclerModel.options.toMutableList()
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        if(!adapterList.contains(adapter))adapterList.add(adapter)
        holder.spinner.adapter = adapter
        holder.spinner.onItemSelectedListener=this
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(ItemView: View, val context: Context) : RecyclerView.ViewHolder(ItemView) {
        var facilityName: TextView = itemView.findViewById(R.id.facility_name)
        var spinner: Spinner = itemView.findViewById(R.id.spinner)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        exclusionList.map {
            val adapterId=it[1].facility_id-1
            tempList.clear()
            for(option in Facilities.facilities[adapterId].options){
                tempList.add(Option(option.id,option.name,option.icon))
            }
            adapterList[adapterId].clear()
            if(it[0].options_id.toString() == p0?.selectedItem.toString()){
                for(option in tempList){
                    if(option.id.toString() == it[1].options_id.toString())continue
                    adapterList[adapterId].insert(option,adapterList[adapterId].count)
                }
            }
            else{
                for(option in tempList){
                    adapterList[adapterId].insert(option,adapterList[adapterId].count)
                }
            }
            adapterList[adapterId].notifyDataSetChanged()
            for(option in tempList){
                if(!Facilities.facilities[adapterId].options.contains(option))Facilities.facilities[adapterId].options.add(option)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        println("nothing selected")
    }

}