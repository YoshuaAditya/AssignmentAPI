package com.example.assignmentapi.objects

data class Option(val id:Int,val name:String,val icon:String){
    override fun toString() :String {
        return this.id.toString()
    }
}
