package com.example.rnm.feature.personage.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Personage
import com.example.rnm.R
import com.example.rnm.feature.interf.AddImage
import com.example.rnm.feature.interf.OnLoadNextPage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PersonageAdapter(
    private val onClick: (Int) -> Unit,
    private val callback: OnLoadNextPage
) : RecyclerView.Adapter<PersonageAdapter.PersonageViewHolder>() {

    var personageList = arrayListOf<Personage>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonageViewHolder =
        PersonageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.personage_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: PersonageViewHolder, position: Int) {
        if ((position >= itemCount - 1)) {
            callback.onLoad()
        }
        holder.bind(personageList[position])
        holder.itemView.setOnClickListener {
            onClick(personageList[position].personageId)
        }
    }

    override fun getItemCount(): Int = personageList.size

    fun setDate(newPersonageList: ArrayList<Personage>) {
        val difUtil = PersonContactDiff(personageList, newPersonageList)
        val difRes = DiffUtil.calculateDiff(difUtil)
        personageList = newPersonageList
        difRes.dispatchUpdatesTo(this)
    }

    class PersonageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val personageImage: ImageView = itemView.findViewById(R.id.personageImage)
        private val personageName: TextView = itemView.findViewById(R.id.personageName)
        private val personageSpecies: TextView = itemView.findViewById(R.id.personageSpecies)
        private val personageStatus: TextView = itemView.findViewById(R.id.personageStatus)
        private val personageGender: TextView = itemView.findViewById(R.id.personageGender)

        fun bind(personage: Personage) {
            AddImage.loadingImage(personage.personageImage, personageImage)
            personageName.text = personage.personageName
            personageSpecies.text = personage.personageSpecies
            personageStatus.text = personage.personageStatus
            personageGender.text = personage.personageGender
        }
    }

    class PersonContactDiff(
        private var oldList: List<Personage>, private var newList: List<Personage>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].personageName == newList[newItemPosition].personageName
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
        override fun getNewListSize(): Int = newList.size
        override fun getOldListSize(): Int = oldList.size
    }
}
