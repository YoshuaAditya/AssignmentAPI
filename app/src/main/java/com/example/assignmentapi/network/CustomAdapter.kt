package com.example.assignmentapi.network

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.example.assignmentapi.R
import com.example.assignmentapi.objects.Option

class CustomAdapter(
    context: Context,
    textViewResourceId: Int,
    itemList: List<Option>
) : ArrayAdapter<Option>(
    context, textViewResourceId,itemList
){
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup) =
        getImageForPosition(position,parent)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup) =
        getImageForPosition(position,parent)

    private fun getImageForPosition(position: Int,parent: ViewGroup):View{
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_spinner_items, parent, false)
        view.findViewById<TextView>(R.id.option_name).text = getItem(position)?.name
        getItem(position)?.apply {
            val id = context.resources.getIdentifier(this.icon.replace("-","_"), "drawable", context.packageName)
            view.findViewById<ImageView>(R.id.option_image).setImageResource(id)
        }
        return view
    }
//    = ImageView(context).apply {
//        setImageResource(R.drawable.apartment)
//        layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
//    }
}