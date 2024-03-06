package com.vaibhav.foodiecart.model

data class CartItems(
    var foodName: String? = null,
    var foodPrice: String? = null,
    var foodDiscription: String? = null,
    var foodImage: String? = null,
    var foodQuantity: Int? = null,
    var foodIngredint: String? = null
)