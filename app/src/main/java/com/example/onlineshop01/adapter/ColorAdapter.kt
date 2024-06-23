package com.example.onlineshop01.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop01.R
import com.example.onlineshop01.databinding.ViewholderColorBinding

class ColorAdapter(
    val items: MutableList<String>
) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    private var selectedPosition = -1;
    private var lastSelectedPosition = -1;
    private lateinit var context: Context;

    /**
     *
     * @param parent The parent view group.
     * @param viewType The type of the view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context;
        val binding = ViewholderColorBinding.inflate(LayoutInflater.from(context), parent, false);

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
        Glide.with(holder.itemView.context)
            .load(items[holder.adapterPosition])
            .into(holder.binding.pic);

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(selectedPosition);
            notifyItemChanged(lastSelectedPosition);
        };

        if (selectedPosition == position) {
            holder.binding.colorLayout.setBackgroundResource(R.drawable.grey_bg_selected);
        } else {
            holder.binding.colorLayout.setBackgroundResource(R.drawable.grey_bg);
        }
    }

    /**
     * A [RecyclerView.ViewHolder] that holds the brand item layout.
     * @param binding The view of the brand item.
     */
    inner class ViewHolder(
        val binding: ViewholderColorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    }
}