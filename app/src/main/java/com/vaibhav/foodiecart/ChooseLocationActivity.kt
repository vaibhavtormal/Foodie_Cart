package com.vaibhav.foodiecart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.vaibhav.foodiecart.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locationList = arrayOf(
            "Pune",
            "Mumbai",
            "Chikhali",
            "Nagpur",
            "Mehkar",
            "Budhana",
            "Amravati",
            "Wagholi,Pune",
            "Kothrud,Pune",
            "Karve Nagar,Pune",
            "Viman Nagar,Pune",
            "Satara",
            "Dhad",
            "Latur",
            "Nandedh"

        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }
}