package com.example.nammametrocloneapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StationAdapter(
    private var stations: List<String>,
    private val onStationClick: (String) -> Unit
) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    class StationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvStationName: TextView = view.findViewById(R.id.tvStationName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_station, parent, false)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = stations[position]
        holder.tvStationName.text = station
        holder.itemView.setOnClickListener {
            onStationClick(station)
        }
    }

    override fun getItemCount() = stations.size

    fun updateData(newStations: List<String>) {
        stations = newStations
        notifyDataSetChanged()
    }
}
