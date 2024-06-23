package com.example.onlineshop01.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop01.model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel() : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance();
    private val _banner = MutableLiveData<List<SliderModel>>();

    val banners: LiveData<List<SliderModel>> = _banner;

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
}