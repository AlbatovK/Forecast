package com.albatros.forecast.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.albatros.forecast.R
import com.albatros.forecast.databinding.PartLayoutBinding
import com.albatros.forecast.domain.getDaytimeDescription
import com.albatros.forecast.domain.isDaytimeDescription
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
                partName.text = if (it.partName.isDaytimeDescription()) it.partName.getDaytimeDescription()
                else context.getString(R.string.unknown_condition)
                icon.loadSvgInto(link.format(if (it.icon.isEmpty()) "ovc" else it.icon))
                avg.text = context.getString(R.string.temp_data, it.tempAvg.toInt())
                pressureFact.text = context.getString(
                    R.string.pressure_mm_data,
                    it.pressureMm.toInt()
                )
                minFact.text = context.getString(
                    R.string.temp_data,
                    it.tempMin.toInt()
                )
                maxFact.text = context.getString(
                    R.string.temp_data,
                    it.tempMax.toInt()
                )
                windSpeedFact.text = context.getString(
                    R.string.wind_speed_fact,
                    (it.windSpeed).toString()
                )
                partName.visibility = View.VISIBLE
                pressureFact.visibility = View.VISIBLE
                icon.visibility = View.VISIBLE
                avg.visibility = View.VISIBLE
                minFact.visibility = View.VISIBLE
                maxFact.visibility = View.VISIBLE
                windSpeedFact.visibility = View.VISIBLE
            }
        }
    }
}