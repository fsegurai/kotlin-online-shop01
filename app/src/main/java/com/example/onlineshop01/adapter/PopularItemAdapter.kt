package com.example.onlineshop01.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.onlineshop01.databinding.ViewholderRecommendedBinding
import com.example.onlineshop01.model.ItemModel

class PopularItemAdapter(
    val items: MutableList<ItemModel>
) : RecyclerView.Adapter<PopularItemAdapter.ViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context;
        val binding =
            ViewholderRecommendedBinding.inflate(LayoutInflater.from(context), parent, false);

        return ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.titleTxt.text = items[position].title;
        holder.binding.priceTxt.text = "$${items[position].price}";
        holder.binding.ratingTxt.text = items[position].rating.toString();

        val requestOptions = RequestOptions().transform(CenterCrop());
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.pic);

//        holder.binding.root.setOnClickListener {
//            val intent = Intent(holder.itemView.context, ItemDetailActivity::class.java);
//        }
    }

    /**
     * Get the number of items in the list.
     */
    override fun getItemCount(): Int = items.size


    class ViewHolder(val binding: ViewholderRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}