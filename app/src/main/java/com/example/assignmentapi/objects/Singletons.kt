package com.example.assignmentapi.objects

import com.example.assignmentapi.objects.Exclusion
import com.example.assignmentapi.objects.Facility

object Facilities{
    var facilities:MutableList<Facility> = mutableListOf()
}
object Exclusions{
    var exclusions:MutableList<MutableList<Exclusion>> = mutableListOf()
    var date= System.currentTimeMillis()
}
