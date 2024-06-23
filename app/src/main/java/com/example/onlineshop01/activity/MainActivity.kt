package com.example.onlineshop01.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.onlineshop01.R
import com.example.onlineshop01.adapter.BrandAdapter
import com.example.onlineshop01.adapter.SliderAdapter
import com.example.onlineshop01.databinding.ActivityMainBinding
import com.example.onlineshop01.model.SliderModel
import com.example.onlineshop01.viewModel.MainViewModel

class MainActivity : BaseActivity() {
    private val viewModel = MainViewModel();
    private lateinit var binding: ActivityMainBinding;

    /**
     * Create the main activity.
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        };

        initBanner();
        initBrand();
    }

    /**
     * Initialize the banner.
     */
    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE;
        viewModel.banners.observe(this, Observer { items ->
            banners(items);
            binding.progressBarBanner.visibility = View.GONE;
        });

        viewModel.loadBanners();
    }

    /**
     * Display the banners.
     * @param images The list of images to display.
     */
    private fun banners(images: List<SliderModel>) {
        binding.viewpagerSlider.adapter = SliderAdapter(images, binding.viewpagerSlider);
        binding.viewpagerSlider.clipToPadding = false;
        binding.viewpagerSlider.clipChildren = false;
        binding.viewpagerSlider.offscreenPageLimit = 3;
        binding.viewpagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER;

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40));
        }

        binding.viewpagerSlider.setPageTransformer(compositePageTransformer);

        if (images.size > 1) {
            binding.dotsIndicator.visibility = View.VISIBLE;
            binding.dotsIndicator.attachTo(binding.viewpagerSlider);
        }
    }

    /**
     * Initialize the brand.
     */
    private fun initBrand() {
        binding.progressBarBrand.visibility = View.VISIBLE;
        viewModel.brands.observe(this, Observer {
            binding.viewBrand.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false);
            binding.viewBrand.adapter = BrandAdapter(it);
            binding.progressBarBrand.visibility = View.GONE;
        });

        viewModel.loadBrand();
    }
}