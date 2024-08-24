package com.potatomeme.doctorappointment.xml.presentation.main

import android.graphics.Picture
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.potatomeme.doctorappointment.xml.presentation.databinding.ViewholderCategoryBinding
import com.potatomeme.doctorappointment.xml.presentation.domain.model.CategoryModel

class CategoryAdapter(private val items: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: CategoryModel) {
            binding.apply {
                titleTxt.text = currentItem.Name
                Glide.with(root)
                    .load(currentItem.Picture)
                    .into(img)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val binding =
            ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) =
        items[position].let { currentItem ->
            holder.bind(currentItem)
        }


    override fun getItemCount(): Int = items.size
}