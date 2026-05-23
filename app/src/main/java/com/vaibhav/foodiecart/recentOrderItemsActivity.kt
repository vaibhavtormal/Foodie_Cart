package com.vaibhav.foodiecart

import OrderDetails
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaibhav.foodiecart.Adapter.RecentBuyAdapter
import com.vaibhav.foodiecart.databinding.ActivityRecentOrderItemsBinding


class recentOrderItemsActivity : AppCompatActivity() {

    private val  binding :ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allfoodName :ArrayList<String>
    private lateinit var allfoodImages :ArrayList<String>
    private lateinit var allfoodPrices :ArrayList<String>
    private lateinit var allfoodQuantitys :ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val recentOrderItems = intent.getSerializableExtra("RecentBuyOrderItem")as ArrayList<OrderDetails>
        recentOrderItems ?.let { orderDetails ->
            if (orderDetails.isNotEmpty()){
                val  recentOrderItem = orderDetails[0]
                allfoodName = recentOrderItem.foodNames as ArrayList<String>
                allfoodImages = recentOrderItem.foodImages as ArrayList<String>
                allfoodPrices = recentOrderItem.foodPrices as ArrayList<String>
                allfoodQuantitys = recentOrderItem.foodQuantities as ArrayList<Int>
                binding.BackButton.setOnClickListener{
                    finish()
                }
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        val  rv = binding.recylcerViewRecentBuyItem
        rv.layoutManager = LinearLayoutManager(this)
        val  adapter = RecentBuyAdapter(this,allfoodName,allfoodImages,allfoodPrices,allfoodQuantitys)
        rv.adapter = adapter
    }
}