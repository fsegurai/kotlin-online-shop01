package com.example.onlineshop01.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.request.RequestOptions
import com.example.onlineshop01.R
import com.example.onlineshop01.model.SliderModel

class SliderAdapter(
    private var sliderItems: List<SliderModel>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private lateinit var context: Context;
    private val runnable = Runnable {
        sliderItems = sliderItems;
        notifyDataSetChanged();
    }

    /**
     * Create a new [SliderViewHolder] and initializes it with the layout of the slider item.
     * @param parent The parent view group.
     * @param viewType The type of the view.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        context = parent.context;
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_item_container, parent, false);

        return SliderViewHolder(view);
    }

    /**
     * Bind the slider item to the [SliderViewHolder].
     * @param holder The [SliderViewHolder] to bind the slider item to.
     * @param position The position of the slider item in the list.
     */
    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position], context);

        if (position == sliderItems.lastIndex - 1) {
            viewPager2.post(runnable);
        }
    }

    /**
     * Get the number of slider items.
     */
    override fun getItemCount(): Int = sliderItems.size;

    /**
     * A [RecyclerView.ViewHolder] that holds the slider item layout.
     * @param itemView The view of the slider item.
     */
    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide);

        fun setImage(sliderItems: SliderModel, context: Context) {
            val requestOptions = RequestOptions().transform(CenterInside());

            Glide.with(context)
                .load(sliderItems.url)
                .apply(requestOptions)
                .into(imageView);
        }
    }
}