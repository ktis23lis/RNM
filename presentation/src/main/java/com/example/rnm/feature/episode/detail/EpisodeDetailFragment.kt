package com.example.rnm.feature.episode.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.domain.interactors.IDetailInteractor
import com.example.rnm.databinding.FragmentEpisodeDetailsBinding
import com.example.rnm.di.Injector
import com.example.rnm.feature.base.BaseFragment
import com.example.rnm.feature.episode.list.EpisodeListFragment
import com.example.rnm.feature.interf.GoBackFragment
import com.example.rnm.feature.interf.OnLoadNextPage
import com.example.rnm.feature.personage.list.PersonageAdapter
import com.example.rnm.feature.personage.list.PersonageListFragment
import javax.inject.Inject

class EpisodeDetailFragment : BaseFragment<EpisodeDetailViewModel>() {

    private var myId = 0
    private lateinit var goPersonageBack: GoBackFragment
    private lateinit var personageAdapter: PersonageAdapter
    private lateinit var binding : FragmentEpisodeDetailsBinding
    private lateinit var itemPersonageSelected: PersonageListFragment.ItemPersonageSelected

    companion object{
        fun newInstance(id: Int) = EpisodeDetailFragment().apply {
            val bundle = Bundle()
            bundle.putInt(EpisodeListFragment.EPISODE_LIST_TAG, id)
            arguments = bundle
        }
    }

    @Inject
    lateinit var episodeInteractor: IDetailInteractor

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        goPersonageBack = context as GoBackFragment
        itemPersonageSelected = context as PersonageListFragment.ItemPersonageSelected
        Injector.episodeDetailFragmentComponent.inject(this)
        injectViewModel()
        personageAdapter = PersonageAdapter( {i ->
            itemPersonageSelected.onItemPersonageSelected(i)
        },object : OnLoadNextPage {
            override fun onLoad() {
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            myId = requireArguments().getInt(EpisodeListFragment.EPISODE_LIST_TAG)
            viewModel.getEpisode(myId)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        episodeDetailRecyclerView.adapter = personageAdapter

        viewModel.episodeLiveData.observe(viewLifecycleOwner) {
            episodeName.text = it.episodeName
            episodeNumber.text = it.episode
            episodeAirFate.text = it.episodeAirFate

        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        toolbar.setNavigationOnClickListener {
            goPersonageBack.onGoingBack()
        }
        viewModel.personageLiveData.observe(viewLifecycleOwner) {
            personageAdapter.personageList = it
        }
    }
}