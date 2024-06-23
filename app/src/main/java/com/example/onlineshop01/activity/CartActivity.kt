package com.example.onlineshop01.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop01.R
import com.example.onlineshop01.adapter.CartAdapter
import com.example.onlineshop01.databinding.ActivityCartBinding
import com.example.onlineshop01.helper.ChangeNumberItemsListener
import com.example.onlineshop01.helper.ManagementCart

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding;
    private lateinit var managementCart: ManagementCart;

    private var tax: Double = 0.0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater);
        setContentView(binding.root);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        managementCart = ManagementCart(this);

        setVariable();
        initCartList();
        calculateCart();
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener {
            finish();
        }
    }

    private fun initCartList() {
        binding.viewCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        binding.viewCart.adapter =
            CartAdapter(managementCart.getListCart(), this, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart();
                }
            });

        with(binding) {
            emptyTxt.visibility =
                if (managementCart.getListCart().isEmpty()) View.VISIBLE else View.GONE;
            scrollView2.visibility =
                if (managementCart.getListCart().isEmpty()) View.GONE else View.VISIBLE;
        }
    }

    private fun calculateCart() {
        val percentTax = 0.02;
        val deliveryFee = 10.0;

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;

        val total = Math.round((managementCart.getTotalFee() + tax + deliveryFee) * 100.0) / 100.0;
        val itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;

        with(binding) {
            totalFeeTxt.text = "$$itemTotal";
            taxTxt.text = "$$tax";
            deliveryTxt.text = "$$deliveryFee";
            totalTxt.text = "$$total";
        }
    }
}