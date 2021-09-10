package com.example.rnm.feature.episode.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Episode
import com.example.rnm.R
import com.example.rnm.feature.interf.OnLoadNextPage

class EpisodeAdapter(
    private val onClick: (Int) -> Unit,
    private val callback: OnLoadNextPage
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    var episodeList = arrayListOf<Episode>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.episode_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        if ((position >= itemCount - 1))
            callback.onLoad()
        holder.bind(episodeList[position])
        holder.itemView.setOnClickListener {
            onClick(episodeList[position].episodeId)
        }
    }

    override fun getItemCount() = episodeList.size

    fun setDate(newEpisodeList: ArrayList<Episode>) {
        val difUtil = EpisodeContactDiff(episodeList, newEpisodeList)
        val difRes = DiffUtil.calculateDiff(difUtil)
        episodeList = newEpisodeList
        difRes.dispatchUpdatesTo(this)
    }

    class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val episodeName: TextView = itemView.findViewById(R.id.episodeName)
        private val episodeNumber: TextView = itemView.findViewById(R.id.episodeNumber)
        private val episodeAirFate: TextView = itemView.findViewById(R.id.episodeAirFate)

        fun bind(episode: Episode) {
            episodeName.text = episode.episodeName
            episodeNumber.text = episode.episode
            episodeAirFate.text = episode.episodeAirFate
        }
    }

    class EpisodeContactDiff(
        private var oldList: List<Episode>, private var newList: List<Episode>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].episodeName == newList[newItemPosition].episodeName
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getNewListSize(): Int = newList.size
        override fun getOldListSize(): Int = oldList.size
    }
}