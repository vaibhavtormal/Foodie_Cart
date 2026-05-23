package com.vaibhav.foodiecart.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vaibhav.foodiecart.DetailsActivity
import com.vaibhav.foodiecart.databinding.MenuItemBinding
import com.vaibhav.foodiecart.model.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext: Context
) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        anim(holder.itemView)
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size
    inner class MenuViewHolder(private val binnding: MenuItemBinding) :
        RecyclerView.ViewHolder(binnding.root) {
        init {
            binnding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailActivity(position)
                }

            }
        }


        private fun openDetailActivity(position: Int) {
            val menuItem = menuItems[position]

            //a intent to open details activity and pass data
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.foodName)
                putExtra("MenuItemImage", menuItem.foodImage)
                putExtra("MenuItemDiscription", menuItem.foodDesription)
                putExtra("MenuItemIngredients", menuItem.foodIngredient)
                putExtra("MenuItemPrice", menuItem.foodPrice)
            }
            //start the details activity
            requireContext.startActivity(intent)
        }

        //set data in to recylerview items name , price , image
        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binnding.apply {
                menuFoodName.text = menuItem.foodName
                menuprice.text = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuImage)
            }
        }

    }
    fun anim(view: View){
        var animation = AlphaAnimation(0.0f,1.0f)
        animation.duration = 1500
        view.startAnimation(animation)
    }

}



