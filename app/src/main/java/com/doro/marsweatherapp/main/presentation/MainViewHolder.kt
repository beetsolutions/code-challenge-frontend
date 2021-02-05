package com.doro.marsweatherapp.main.presentation

import androidx.recyclerview.widget.RecyclerView
import com.doro.marsweatherapp.databinding.ItemWeatherBinding
import com.doro.marsweatherapp.main.domain.model.WeatherInformation

class MainViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(sol: WeatherInformation?) {
        sol?.let {
            binding.weather = it
        }
    }
}