package com.albatros.forecast.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albatros.forecast.databinding.PartLayoutBinding
import com.albatros.forecast.model.data.Part

class PartAdapter(private val parts: List<Part>) : RecyclerView.Adapter<PartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartViewHolder {
        val binding = PartLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return PartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PartViewHolder, position: Int) {
        val item = parts[position]
        holder.part = item
    }

    override fun getItemCount(): Int = parts.size
}