package com.potatomeme.doctorappointment.xml.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.potatomeme.doctorappointment.xml.presentation.databinding.ViewholderMainTopDoctorBinding
import com.potatomeme.doctorappointment.xml.presentation.databinding.ViewholderTopDoctorBinding
import com.potatomeme.doctorappointment.xml.presentation.domain.model.DoctorsModel

class MainTopDoctorAdapter(
    private val items: List<DoctorsModel>,
    private val itemClickListener: (DoctorsModel) -> Unit,
) : RecyclerView.Adapter<MainTopDoctorAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ViewholderMainTopDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: DoctorsModel) {
            binding.apply {
                nameTxt.text = currentItem.Name
                specialTxt.text = currentItem.Special
                scoreTxt.text = "${currentItem.Rating}"
                yearTxt.text = "${currentItem.Expriense} Year"

                Glide.with(itemView)
                    .load(currentItem.Picture)
                    .apply { RequestOptions().transform(CenterCrop()) }
                    .into(img)

                itemView.setOnClickListener { itemClickListener(currentItem) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderMainTopDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        items[position].let { currentItem ->
            holder.bind(currentItem)
        }

    override fun getItemCount(): Int = items.size
}