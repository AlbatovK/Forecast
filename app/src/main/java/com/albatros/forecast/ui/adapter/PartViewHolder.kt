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
                partName.text = if (it.partName.isNotEmpty())  it.partName.getDaytimeDescription()
                else context.getString(R.string.unknown_condition)
                icon.loadSvgInto(link.format(if (it.icon.isEmpty()) "ovc" else it.icon))
                avg.text = context.getString(R.string.temp_data, it.tempAvg.toInt())
                pressure.text = context.getString(
                    R.string.pressure_mm,
                    it.pressureMm.toInt()
                )
                tempMin.text = context.getString(
                    R.string.min_max,
                    context.getString(R.string.temp_data, it.tempMin.toInt()),
                    context.getString(R.string.temp_data, it.tempMax.toInt())
                )
                windSpeed.text = context.getString(
                    R.string.wind_speed,
                    (it.windSpeed).toString()
                )
                partName.visibility = View.VISIBLE
                icon.visibility = View.VISIBLE
                avg.visibility = View.VISIBLE
                tempMin.visibility = View.VISIBLE
                pressure.visibility = View.VISIBLE
                windSpeed.visibility = View.VISIBLE
            }
        }
    }
}