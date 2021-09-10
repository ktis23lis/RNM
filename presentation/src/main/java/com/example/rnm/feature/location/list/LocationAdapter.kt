package com.example.rnm.feature.location.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Location
import com.example.rnm.R
import com.example.rnm.feature.interf.OnLoadNextPage

class LocationAdapter(
    private val onClick: (Int) -> Unit,
    private val callback: OnLoadNextPage
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    var locationList = arrayListOf<Location>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
        LocationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.location_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        if ((position >= itemCount - 10))
            callback.onLoad()
        holder.bind(locationList[position])
        holder.itemView.setOnClickListener {
            onClick(locationList[position].locationId)
        }
    }

    override fun getItemCount() = locationList.size

    fun setDate(newLocationList: ArrayList<Location>) {
        val difUtil = LocationContactDiff(locationList, newLocationList)
        val difRes = DiffUtil.calculateDiff(difUtil)
        locationList = newLocationList
        difRes.dispatchUpdatesTo(this)
    }

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val locationName: TextView = itemView.findViewById(R.id.locationName)
        private val locationType: TextView = itemView.findViewById(R.id.locationType)
        private val locationDimension: TextView = itemView.findViewById(R.id.locationDimension)

        fun bind(location: Location) {
            locationName.text = location.locationName
            locationType.text = location.locationType
            locationDimension.text = location.locationDimension
        }
    }

    class LocationContactDiff(
        private var oldList: List<Location>, private var newList: List<Location>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].locationName == newList[newItemPosition].locationName
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getNewListSize(): Int = newList.size
        override fun getOldListSize(): Int = oldList.size
    }
}