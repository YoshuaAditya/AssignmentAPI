package com.example.assignmentapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentapi.databinding.ActivityMainBinding
import com.example.assignmentapi.objects.Exclusion
import com.example.assignmentapi.objects.Exclusions
import com.example.assignmentapi.objects.Facilities
import com.example.assignmentapi.objects.Facility
import com.example.assignmentapi.network.APIClient
import com.example.assignmentapi.network.ApiInterface
import com.example.assignmentapi.network.RecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var facilityList = mutableListOf<Facility>()
    var exclusionList = mutableListOf<MutableList<Exclusion>>()
    private lateinit var databinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val isDayPassed = (System.currentTimeMillis() - Exclusions.date) >= TimeUnit.DAYS.toMillis(1)
        println(Exclusions.exclusions.isEmpty()|| isDayPassed)
        if(Exclusions.exclusions.isEmpty()|| isDayPassed){
            Exclusions.exclusions.clear()
            Facilities.facilities.clear()
            println("day changed or empty")
            Exclusions.date=System.currentTimeMillis()
            getExclusions()
        }
        else{
            populateRecyclerView()
        }
    }

    private fun populateRecyclerView() {
        Facilities.facilities.map {
            facilityList.add(Facility(it.facility_id,it.name,it.options))
        }
        exclusionList=Exclusions.exclusions.toMutableList()
        val adapter = RecyclerAdapter(facilityList,exclusionList)
        databinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        databinding.recyclerView.adapter = adapter
    }

    private fun getExclusions() {
        val client = APIClient().getRetrofitClient().create(ApiInterface::class.java)
        client.getExclusions().enqueue(object : Callback<MutableList<MutableList<Exclusion>>> {
            override fun onResponse(
                call: Call<MutableList<MutableList<Exclusion>>>,
                response: Response<MutableList<MutableList<Exclusion>>>
            ) {
                // Used for inserting data in MutableList of type Facility
                Exclusions.exclusions.addAll(response.body()!!)
                getFacilities()
            }

            override fun onFailure(call: Call<MutableList<MutableList<Exclusion>>>, t: Throwable) {
                println(t)
            }

        })
    }

    private fun getFacilities() {
        val client = APIClient().getRetrofitClient().create(ApiInterface::class.java)
        client.getFacilities().enqueue(object : Callback<MutableList<Facility>> {
            override fun onResponse(
                call: Call<MutableList<Facility>>,
                response: Response<MutableList<Facility>>
            ) {
                // Used for inserting data in MutableList of type Facility
                Facilities.facilities.addAll(response.body()!!)
                populateRecyclerView()
            }

            override fun onFailure(call: Call<MutableList<Facility>>, t: Throwable) {
                println(t)
            }

        })
    }
}