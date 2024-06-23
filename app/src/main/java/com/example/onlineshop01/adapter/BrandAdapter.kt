package com.example.onlineshop01.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop01.R
import com.example.onlineshop01.databinding.ViewholderBrandBinding
import com.example.onlineshop01.model.BrandModel

class BrandAdapter(
    val items: MutableList<BrandModel>
) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {

    private var selectedPosition = -1;
    private var lastSelectedPosition = -1;
    private lateinit var context: Context;

    /**
     * Create a new [ViewHolder] and initializes it with the layout of the brand item.
     * @param parent The parent view group.
     * @param viewType The type of the view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context;
        val binding = ViewholderBrandBinding.inflate(LayoutInflater.from(context), parent, false);

        return ViewHolder(binding);
    }

    /**
     * Get the number of brand items.
     */
    override fun getItemCount(): Int = items.size;

    /**
     * Bind the brand item to the [ViewHolder].
     * @param holder The [ViewHolder] to bind the brand item to.
     * @param position The position of the brand item in the list.
     */
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[holder.adapterPosition];
        holder.binding.title.text = item.title;

        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.pic);

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(selectedPosition);
            notifyItemChanged(lastSelectedPosition);
        };

        holder.binding.title.setTextColor(context.resources.getColor(R.color.white));

        if (selectedPosition == position) {
            holder.binding.pic.setBackgroundResource(0);
            holder.binding.mainLayout.setBackgroundResource(R.drawable.purple_bg);
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(context.getColor(R.color.white))
            );

            holder.binding.title.visibility = View.VISIBLE;
        } else {
            holder.binding.pic.setBackgroundResource(R.drawable.grey_bg);
            holder.binding.mainLayout.setBackgroundResource(0);
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(context.getColor(R.color.black))
            );

            holder.binding.title.visibility = View.GONE;
        }
    }

    /**
     * A [RecyclerView.ViewHolder] that holds the brand item layout.
     * @param binding The view of the brand item.
     */
    class ViewHolder(
        val binding: ViewholderBrandBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    }
}