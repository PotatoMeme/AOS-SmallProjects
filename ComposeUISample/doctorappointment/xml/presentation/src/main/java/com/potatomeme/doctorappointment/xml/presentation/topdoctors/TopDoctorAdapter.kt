package com.potatomeme.doctorappointment.xml.presentation.topdoctors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.potatomeme.doctorappointment.xml.presentation.databinding.ViewholderTopDoctorBinding
import com.potatomeme.doctorappointment.xml.presentation.domain.model.DoctorsModel

class TopDoctorAdapter(
    private val items: List<DoctorsModel>,
    private val onItemClickListener: (DoctorsModel) -> Unit
    ) :
    RecyclerView.Adapter<TopDoctorAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ViewholderTopDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DoctorsModel) {
            binding.apply {
                nameTxt.text = item.Name
                specialTxt.text = item.Special
                scoreTxt.text = item.Rating.toString()
                ratingBar.rating = item.Rating.toFloat()
                scoreTxt.text = item.Rating.toString()
                degreeTxt.text = "Professional Doctor"

                Glide.with(itemView.context)
                    .load(item.Picture)
                    .apply { RequestOptions().transform(CenterCrop()) }
                    .into(img)

                makeBtn.setOnClickListener{
                    onItemClickListener(item)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderTopDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        items[position].let { item ->
            holder.bind(item)
        }

    override fun getItemCount(): Int = items.size
}