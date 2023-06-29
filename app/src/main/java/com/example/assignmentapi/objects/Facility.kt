package com.example.assignmentapi.objects

import com.example.assignmentapi.objects.Option


data class Facility(val facility_id:Int,val name:String, val options:MutableList<Option>){

}
