package com.vaibhav.foodiecart.Adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.vaibhav.foodiecart.DetailsActivity
import com.vaibhav.foodiecart.databinding.PopularItemBinding

class PopularAdapter(
    private val itmes: List<String>,
    private val price: List<String>,
    private val image: List<Int>,
    private val requireContext: Context
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            PopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        anim(holder.itemView)
        val item = itmes[position]
        val price = price[position]
        val images = image[position]
        holder.bind(item, price, images)
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("MenuItemImage", images)
            requireContext.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return itmes.size
    }
    class PopularViewHolder(private val binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imagesView = binding.foodimagePopular
        fun bind(item: String, price: String, images: Int) {
            binding.FoodNamePopular.text = item
            binding.pricepopular.text = price
            imagesView.setImageResource(images)
        }
    }
    fun anim(view: View){
        var animation = AlphaAnimation(0.0f,1.0f)
        animation.duration = 1500
        view.startAnimation(animation)
    }
}