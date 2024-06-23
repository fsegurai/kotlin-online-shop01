package com.example.onlineshop01.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop01.model.BrandModel
import com.example.onlineshop01.model.ItemModel
import com.example.onlineshop01.model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel() : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance();

    private val _banner = MutableLiveData<List<SliderModel>>();
    private val _brand = MutableLiveData<MutableList<BrandModel>>();
    private val _popularItem = MutableLiveData<MutableList<ItemModel>>();

    val banners: LiveData<List<SliderModel>> = _banner;
    val brands: LiveData<MutableList<BrandModel>> = _brand;
    val popularItems: LiveData<MutableList<ItemModel>> = _popularItem;

    /**
     * Load the banners from the Firebase database.
     * The banners are stored in the "Banner" node in the Firebase database.
     */
    fun loadBanners() {
        val databaseReference = firebaseDatabase.getReference("Banner");
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>();

                for (data in snapshot.children) {
                    val model = data.getValue(SliderModel::class.java);

                    if (model != null) {
                        lists.add(model);
                    }
                }

                _banner.value = lists;
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    /**
     * Load the brands from the Firebase database.
     * The brands are stored in the "Category" node in the Firebase database.
     */
    fun loadBrand() {
        val databaseReference = firebaseDatabase.getReference("Category");
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<BrandModel>();

                for (data in snapshot.children) {
                    val model = data.getValue(BrandModel::class.java);

                    if (model != null) {
                        lists.add(model);
                    }
                }

                _brand.value = lists;
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    /**
     * Load the popular items from the Firebase database.
     * The popular items are stored in the "Items" node in the Firebase database.
     */
    fun loadPopularItems() {
        val databaseReference = firebaseDatabase.getReference("Items");
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemModel>();

                for (data in snapshot.children) {
                    val model = data.getValue(ItemModel::class.java);

                    if (model != null) {
                        lists.add(model);
                    }
                }

                _popularItem.value = lists;
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}