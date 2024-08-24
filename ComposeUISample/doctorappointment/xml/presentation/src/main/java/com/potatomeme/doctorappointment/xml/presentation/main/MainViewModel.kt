package com.potatomeme.doctorappointment.xml.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.potatomeme.doctorappointment.xml.presentation.domain.model.CategoryModel
import com.potatomeme.doctorappointment.xml.presentation.domain.model.DoctorsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _doctors = MutableLiveData<MutableList<DoctorsModel>>()

    val category: LiveData<MutableList<CategoryModel>> = _category
    val doctors: LiveData<MutableList<DoctorsModel>> = _doctors


    fun loadCategory() {
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(CategoryModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _category.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadDoctors() {
        val ref = firebaseDatabase.getReference("Doctors")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<DoctorsModel>()

                for (childSnapshot in snapshot.children) {
                    val model = childSnapshot.getValue(DoctorsModel::class.java)

                    if (model != null) {
                        lists.add(model)
                    }
                }
                _doctors.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}