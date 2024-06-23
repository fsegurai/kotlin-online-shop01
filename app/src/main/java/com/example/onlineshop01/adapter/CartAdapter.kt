package com.example.onlineshop01.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.onlineshop01.databinding.ViewholderCartBinding
import com.example.onlineshop01.helper.ChangeNumberItemsListener
import com.example.onlineshop01.helper.ManagementCart
import com.example.onlineshop01.model.ItemModel

class CartAdapter(
    private val listItemSelected: ArrayList<ItemModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener? = null
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val managementCart = ManagementCart(context);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding =
            ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false);

        return ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val item = listItemSelected[holder.adapterPosition];

        holder.binding.titleTxt.text = item.title;
        holder.binding.feeEachItem.text = "$${item.price}";
        holder.binding.totalEachItem.text = "$${Math.round(item.price * item.numberInCart)}";
        holder.binding.numberItemTxt.text = item.numberInCart.toString();

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.pic);

        holder.binding.plusCartBtn.setOnClickListener {
            managementCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged();
                    changeNumberItemsListener?.onChanged();
                }
            });
        }

        holder.binding.minusCartBtn.setOnClickListener {
            managementCart.minusItem(
                listItemSelected,
                position,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        notifyDataSetChanged();
                        changeNumberItemsListener?.onChanged();
                    }
                });
        }
    }

    override fun getItemCount(): Int = listItemSelected.size;

    /**
     * A [RecyclerView.ViewHolder] that holds the brand item layout.
     * @param binding The view of the brand item.
     */
    inner class ViewHolder(
        val binding: ViewholderCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    }
}