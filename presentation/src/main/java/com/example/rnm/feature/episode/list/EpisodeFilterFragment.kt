package com.example.rnm.feature.episode.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.rnm.R
import com.example.rnm.databinding.FragmentEpisodeFilterBinding

class EpisodeFilterFragment : DialogFragment(R.layout.fragment_episode_filter) {

    private lateinit var binding: FragmentEpisodeFilterBinding
    private var callback: DialogCallback? = null

    companion object {
        fun newInstance() = EpisodeFilterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callback = parentFragment as DialogCallback?
        binding = FragmentEpisodeFilterBinding.inflate(inflater, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        seasonFilter.setSelection(
            requireContext().resources.getStringArray(R.array.seasons)
                .indexOf(
                    EpisodeListFragment.filterEpisode.season?.split("0")?.last()
                )
        )

        buttonOk.setOnClickListener {
            EpisodeListFragment.filterEpisode.apply {
                season = if (seasonFilter.selectedItem.toString() != "All") {
                    "S0${seasonFilter.selectedItem}"
                } else null
            }
            callback?.onFilter()
            dialog?.dismiss()
        }
    }

    interface DialogCallback {
        fun onFilter()
    }
}