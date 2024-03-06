package com.vaibhav.foodiecart.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vaibhav.foodiecart.R
import com.vaibhav.foodiecart.databinding.FragmentProfileBinding
import com.vaibhav.foodiecart.model.UserModel


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setUserData()
        binding.saveInfoButton.setOnClickListener {
            val name = binding.profilename.text.toString()
            val email = binding.profileEmail.text.toString()
            val address = binding.profileAddresss.text.toString()
            val phone = binding.profilePhone.text.toString()
            updateUserData(name, email, address, phone)
        }
        return binding.root

    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {
        val userId: String? = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("user").child(userId)

            val userData: HashMap<String, String> = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Profile Update, Successfully\uD83D\uDE07",
                    Toast.LENGTH_SHORT
                ).show()

            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Profile Update, Failed\uD83D\uDE1E",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null) {
                            binding.profilename.setText(userProfile.name)
                            binding.profileAddresss.setText(userProfile.address)
                            binding.profileEmail.setText(userProfile.email)
                            binding.profilePhone.setText(userProfile.phone)

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }

}