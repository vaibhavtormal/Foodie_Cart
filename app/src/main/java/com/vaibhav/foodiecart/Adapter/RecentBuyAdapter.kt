package com.vaibhav.foodiecart.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vaibhav.foodiecart.databinding.RecentBuyItemBinding

class RecentBuyAdapter(
    private var contrxt: Context,
    private val foodNameList: ArrayList<String>,
    private val foodImageList: ArrayList<String>,
    private val foodPriceList: ArrayList<String>,
    private val foodQuantityList: ArrayList<Int>,

    ) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding =
            RecentBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int = foodNameList.size
    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        anim(holder.itemView)
        holder.bind(position)
    }

   inner class RecentViewHolder(private val binding: RecentBuyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text = foodNameList [position]
                foodPrice.text = foodPriceList[position]
                foodQuantity.text = foodQuantityList[position].toString()
                val uriString = foodImageList[position]
                val uri = Uri.parse(uriString)
                Glide.with(contrxt).load(uri).into(foodImage)
            }
        }
    }
    fun anim(view: View){
        var animation = AlphaAnimation(0.0f,1.0f)
        animation.duration = 1500
        view.startAnimation(animation)
    }
}