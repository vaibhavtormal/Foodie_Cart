package com.vaibhav.foodiecart

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.shashank.sony.fancytoastlib.FancyToast
import com.vaibhav.foodiecart.databinding.ActivityDetailsBinding
import com.vaibhav.foodiecart.model.CartItems

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    private var foodName: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredient: String? = null
    private var foodPrice: String? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //intialize FireBaseAuth
        auth = FirebaseAuth.getInstance()
        foodName = intent.getStringExtra("MenuItemName")
        foodDescription = intent.getStringExtra("MenuItemDiscription")
        foodIngredient = intent.getStringExtra("MenuItemIngredients")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodImage = intent.getStringExtra("MenuItemImage")
        with(binding) {
            DetailFoodName.text = foodName
            detailsdescription.text = foodDescription
            detailsingredient.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImageView)

        }

        binding.backButton.setOnClickListener {
            finish()
        }
        binding.addItemButton.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""
        //create a cartItems object
        val cartItem = CartItems(
            foodName.toString(),
            foodPrice.toString(),
            foodDescription.toString(),
            foodImage.toString(),
            1
        )
        //save data to cart item to firebase  data bse
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem)
            .addOnSuccessListener {
                FancyToast.makeText(
                    this,
                    "Items added into cart successfully\uD83E\uDD29",
                    FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false
                ).show()
            }.addOnFailureListener {
            FancyToast.makeText(this, "Item Not added☹\uFE0F", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
        }
    }
}