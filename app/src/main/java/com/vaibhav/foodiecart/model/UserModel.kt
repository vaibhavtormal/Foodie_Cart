package com.vaibhav.foodiecart.model

import android.location.Address

data class UserModel(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phone: String? = null,
    val address: String? = null
)
