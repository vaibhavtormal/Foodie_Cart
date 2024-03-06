package com.vaibhav.foodiecart.Fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vaibhav.foodiecart.Adapter.BuyAgainAdapter
import com.vaibhav.foodiecart.R
import com.vaibhav.foodiecart.databinding.FragmentHistoryBinding
import com.vaibhav.foodiecart.model.OrderDetails


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        //retrieve and display the user Order History
        retrieveBuyHistory()

        return binding.root

    }

    /*   private fun retrieveBuyHistory() {
           binding.recentBuyItem.visibility = View.INVISIBLE
           userId = auth.currentUser?.uid?:""
           val buyItemReference : DatabaseReference = database.reference.child("user").child(userId).child("BuyHistory")
           val shortingQuery = buyItemReference.orderByChild("currenTime")
           shortingQuery.addListenerForSingleValueEvent(object :ValueEventListener{
               override fun onDataChange(snapshot: DataSnapshot) {
                   for (buySnapShot in snapshot.children){
                       val buyHistoryItem = buySnapShot.getValue(OrderDetails::class.java)
                       buyHistoryItem?.let {
                           listOfOrderItem.add(it)
                       }
                   }
                   listOfOrderItem.reverse()
                   if (listOfOrderItem.isNotEmpty()){
                     setDataINRecentBuyItem()
                   }
               }
               override fun onCancelled(error: DatabaseError) {
               }
           })
       }*/
    private fun retrieveBuyHistory() {
        binding.recentBuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""
        val buyItemReference: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery = buyItemReference.orderByChild("currenTime")
        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapShot in snapshot.children) {
                    try {
                        val buyHistoryItem = buySnapShot.getValue(OrderDetails::class.java)
                        buyHistoryItem?.let {
                            listOfOrderItem.add(it)
                        }
                    } catch (e: Exception) {
                        // Handle the exception or log the error
                        Log.e(
                            "FirebaseError",
                            "Failed to convert value to OrderDetails: ${e.message}"
                        )
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {
                    setDataINRecentBuyItem()
                    setPreviousBuyItemsRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if necessary
                Log.e("FirebaseError", "Database Error: ${error.message}")
            }

        })
    }


    private fun setDataINRecentBuyItem() {
        binding.recentBuyItem.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                it.foodNames?.firstOrNull()?.let { name ->
                    buyAgainFoodName.text = name
                }
                it.foodPrices?.firstOrNull()?.let { price ->
                    buyAgainFoodPrice.text = price
                }
                val image = it.foodImages?.firstOrNull()
                image?.let {
                    val uri = Uri.parse(it)
                    Glide.with(requireContext()).load(uri).into(buyAgainFoodImage)


                    listOfOrderItem.reverse()
                    if (listOfOrderItem.isNotEmpty()) {

                    }
                }
            }
        }
    }

    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImage = mutableListOf<String>()
        for (i in 1 until listOfOrderItem.size) {
            listOfOrderItem[i].foodNames?.firstOrNull()?.let {
                buyAgainFoodName.add(it)
                listOfOrderItem[i].foodPrices?.firstOrNull()?.let {
                    buyAgainFoodPrice.add(it)
                    listOfOrderItem[i].foodImages?.firstOrNull()?.let {
                        buyAgainFoodImage.add(it)
                    }
                }
                val rv = binding.buyAgainRecyclerView
                rv.layoutManager = LinearLayoutManager(requireContext())
                buyAgainAdapter = BuyAgainAdapter(
                    buyAgainFoodName,
                    buyAgainFoodPrice,
                    buyAgainFoodImage,
                    requireContext()
                )
                rv.adapter = buyAgainAdapter
            }
        }
    }
}