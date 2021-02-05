package com.doro.marsweatherapp.main.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.doro.marsweatherapp.databinding.ItemWeatherBinding
import com.doro.marsweatherapp.main.domain.model.WeatherInformation

class MainAdapter : ListAdapter<WeatherInformation, MainViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<WeatherInformation> =
                object : DiffUtil.ItemCallback<WeatherInformation>() {
                    override fun areItemsTheSame(oldItem: WeatherInformation, newItem: WeatherInformation): Boolean =
                            oldItem == newItem
                    override fun areContentsTheSame(
                            oldItem: WeatherInformation,
                            newItem: WeatherInformation
                    ): Boolean = oldItem.key == newItem.key
                }
    }
}