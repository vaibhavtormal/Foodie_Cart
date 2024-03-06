package com.vaibhav.foodiecart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vaibhav.foodiecart.Adapter.NotificationAdapter
import com.vaibhav.foodiecart.databinding.FragmentNotificationBottomBinding


class Notification_Bottom_Fragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBottomBinding.inflate(layoutInflater, container, false)
        val notifications = listOf(
            "Your order has been Canceled Succesfully",
            "Order has been taken by the driver",
            "Congrats Your Order Placed"
        )
        val notificationImages = listOf(
            R.drawable.sademoji,
            R.drawable.truck,
            R.drawable.congrats
        )
        val adapter = NotificationAdapter(
            ArrayList(notifications),
            ArrayList(notificationImages)
        )
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter
        return binding.root

    }

    companion object {

    }
}