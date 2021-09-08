package com.albatros.forecast.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.albatros.forecast.R
import com.albatros.forecast.databinding.PartLayoutBinding
import com.albatros.forecast.domain.getDaytimeDescription
import com.albatros.forecast.domain.link
import com.albatros.forecast.domain.loadSvgInto
import com.albatros.forecast.model.data.Part

class PartViewHolder(private val binding: PartLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var part: Part? = null
        get() = field!!
        set(value) {
            field = value
            bind(value)
        }

    private fun bind(part: Part?) {
        val context = binding.root.context
        part?.let {
            with(binding) {
                partName.text = it.partName?.getDaytimeDescription() ?: context.getString(R.string.unknown_condition)
                icon.loadSvgInto(link.format(it.icon ?: "ovc"))
                avg.text = context.getString(R.string.temp_data, part.tempAvg?.toInt() ?: 0)
                (it.tempAvg ?: 0).toString()
                tempMax.text = context.getString(
                    R.string.max_temp,
                    context.getString(R.string.temp_data, it.tempMax?.toInt() ?: 0)
                )
                tempMin.text = context.getString(
                    R.string.min_temp,
                    context.getString(R.string.temp_data, it.tempMin?.toInt() ?: 0)
                )
                feelsLike.text = context.getString(
                    R.string.feels_like,
                    context.getString(R.string.temp_data, it.feelsLike?.toInt() ?: 0)
                )
                partName.visibility = View.VISIBLE
                icon.visibility = View.VISIBLE
                avg.visibility = View.VISIBLE
                tempMin.visibility = View.VISIBLE
                tempMax.visibility = View.VISIBLE
                feelsLike.visibility = View.VISIBLE
            }
        }
    }
}