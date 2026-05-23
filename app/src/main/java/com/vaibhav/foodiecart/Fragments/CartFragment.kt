package com.vaibhav.foodiecart.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.shashank.sony.fancytoastlib.FancyToast
import com.vaibhav.foodiecart.Adapter.CartAdapter
import com.vaibhav.foodiecart.PayOutActivity
import com.vaibhav.foodiecart.databinding.FragmentCartBinding
import com.vaibhav.foodiecart.model.CartItems

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescription: MutableList<String>
    private lateinit var foodImagesUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartKeys: MutableList<String>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        reteriveCartItmes()

        binding.ProceedButton.setOnClickListener {
            getOrderItemDetails()
        }

        return binding.root
    }

    private fun reteriveCartItmes() {
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        // Initialize lists
        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodDescription = mutableListOf()
        foodImagesUri = mutableListOf()
        foodIngredients = mutableListOf()
        quantity = mutableListOf()
        cartKeys = mutableListOf()

        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val cartItems = foodSnapshot.getValue(CartItems::class.java)
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodDiscription?.let { foodDescription.add(it) }
                    cartItems?.foodImage?.let { foodImagesUri.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                    cartItems?.foodIngredint?.let { foodIngredients.add(it) }
                    foodSnapshot.key?.let { cartKeys.add(it) }
                }
                setAdapter(foodReference)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not fetched", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setAdapter(cartItemsReference: DatabaseReference) {
        cartAdapter = CartAdapter(
            requireContext(),
            foodNames,
            foodPrices,
            foodDescription,
            foodImagesUri,
            quantity,
            foodIngredients,
            cartKeys,
            cartItemsReference
        )

        binding.cartrecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartrecyclerView.adapter = cartAdapter
    }

    private fun getOrderItemDetails() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescrption = mutableListOf<String>()
        val foodIngredients = mutableListOf<String>()
        val foodQuantities = cartAdapter.getUpdatedItemsQuantities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDiscription?.let { foodDescrption.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodIngredint?.let { foodIngredients.add(it) }
                }

                orderNow(
                    foodName,
                    foodPrice,
                    foodDescrption,
                    foodImage,
                    foodIngredients,
                    foodQuantities
                )
            }

            override fun onCancelled(error: DatabaseError) {
                FancyToast.makeText(
                    requireContext(),
                    "Order making failed. Please try again later.",
                    FancyToast.LENGTH_SHORT, FancyToast.ERROR, false
                ).show()
            }
        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescrption: MutableList<String>,
        foodImage: MutableList<String>,
        foodIngredients: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putStringArrayListExtra("FoodItemName", ArrayList(foodName))
            intent.putStringArrayListExtra("FoodItemPrice", ArrayList(foodPrice))
            intent.putStringArrayListExtra("FoodItemImage", ArrayList(foodImage))
            intent.putStringArrayListExtra("FoodItemDescription", ArrayList(foodDescrption))
            intent.putStringArrayListExtra("FoodItemIngredient", ArrayList(foodIngredients))
            intent.putIntegerArrayListExtra("FoodItemQuantities", ArrayList(foodQuantities))
            startActivity(intent)
        }
    }

    companion object
}


