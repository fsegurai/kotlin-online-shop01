package com.example.onlineshop01.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop01.R
import com.example.onlineshop01.adapter.ColorAdapter
import com.example.onlineshop01.adapter.SizeAdapter
import com.example.onlineshop01.adapter.SliderAdapter
import com.example.onlineshop01.databinding.ActivityDetailBinding
import com.example.onlineshop01.helper.ManagementCart
import com.example.onlineshop01.model.ItemModel
import com.example.onlineshop01.model.SliderModel

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding;
    private lateinit var item: ItemModel;
    private var numberOrder = 1;
    private lateinit var managementCart: ManagementCart;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        managementCart = ManagementCart(this);

        getBundle();
        banners();
        initLists();
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!;
        binding.titleTxt.text = item.title;
        binding.descriptionTxt.text = item.description;
        binding.priceTxt.text = "$${item.price}";
        binding.ratingTxt.text = "${item.rating} Rating";
        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOrder;
            managementCart.insertFood(item);
        }
        binding.backBtn.setOnClickListener {
            finish();
        }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java));
        }
    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>();

        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl));
        }

        binding.slider.adapter = SliderAdapter(sliderItems, binding.slider);
        binding.slider.clipToPadding = true;
        binding.slider.clipChildren = true;
        binding.slider.offscreenPageLimit = 1;

        if (sliderItems.size > 1) {
            binding.dotsIndicator.visibility = View.VISIBLE;
            binding.dotsIndicator.attachTo(binding.slider);
        }
    }

    private fun initLists() {
        val sizeList = ArrayList<String>();

        for (size in item.size) {
            sizeList.add(size.toString());
        }

        binding.sizeList.adapter = SizeAdapter(sizeList);
        binding.sizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        val colorList = ArrayList<String>();

        for (imageUrl in item.picUrl) {
            colorList.add(imageUrl);
        }

        binding.colorList.adapter = ColorAdapter(colorList);
        binding.colorList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    }
}