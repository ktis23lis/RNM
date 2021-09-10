package com.example.rnm.feature.personage.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.interactors.IDetailInteractor
import com.example.rnm.R
import com.example.rnm.databinding.FragmentPersonageDetailsBinding
import com.example.rnm.di.Injector
import com.example.rnm.feature.base.BaseFragment
import com.example.rnm.feature.episode.list.EpisodeAdapter
import com.example.rnm.feature.episode.list.EpisodeListFragment
import com.example.rnm.feature.interf.AddImage
import com.example.rnm.feature.interf.GoBackFragment
import com.example.rnm.feature.interf.OnLoadNextPage
import com.example.rnm.feature.personage.list.PersonageListFragment
import javax.inject.Inject

class PersonageDetailFragment : BaseFragment<PersonageDetailViewModel>() {

    private var originId = 0
    private var locationId = 0
    private var myId = 0
    private lateinit var goLocation: GoLocation
    private lateinit var goPersonageBack: GoBackFragment
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var itemEpisodeSelected: EpisodeListFragment.ItemEpisodeSelected
    private lateinit var binding: FragmentPersonageDetailsBinding

    companion object {
        fun newInstance(id: Int) = PersonageDetailFragment().apply {
            val bundle = Bundle()
            bundle.putInt(PersonageListFragment.PERSONAGE_LIST_TAG, id)
            arguments = bundle
        }
    }

    @Inject
    lateinit var personageInteractor: IDetailInteractor

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        goPersonageBack = context as GoBackFragment
        goLocation = context as GoLocation
        itemEpisodeSelected = context as EpisodeListFragment.ItemEpisodeSelected
        Injector.personageDetailFragmentComponent.inject(this)
        injectViewModel()
        episodeAdapter = EpisodeAdapter({ i ->
            itemEpisodeSelected.onItemEpisodeSelected(i)
        },
            object : OnLoadNextPage {
                override fun onLoad() {
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            myId = requireArguments().getInt(PersonageListFragment.PERSONAGE_LIST_TAG)
            viewModel.getPersonage(myId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        personageDetailRecyclerView.adapter = episodeAdapter

        viewModel.personageLiveData.observe(viewLifecycleOwner) {
            AddImage.loadingImage(it.personageImage, personageImage)
            personageName.text = it.personageName
            personageSpecies.text = it.personageSpecies
            personageStatus.text = it.personageStatus
            personageGender.text = it.personageGender
            if (it.personageOriginName.url != "") {
                originId = getMyId(it.personageOriginName.url)
                personageOrigin.text = it.personageOriginName.name
            } else {
                originId = 0
                personageOrigin.text = "?"
            }
            if (it.personageLocation.url != "") {
                locationId = getMyId(it.personageLocation.url)
                personageLocation.text = it.personageLocation.name

            } else {
                locationId = 0
                personageLocation.text = "?"
            }
        }
        toolbar.setNavigationOnClickListener {
            goPersonageBack.onGoingBack()
        }

        if (personageOrigin.text != "?") {
            personageOrigin.setOnClickListener {
                goLocation.onGoLocation(originId)
            }
        }
        if (personageLocation.text != "?") {
            personageLocation.setOnClickListener {
                goLocation.onGoLocation(locationId)
            }
        }
        viewModel.episodeLiveData.observe(viewLifecycleOwner) {
            episodeAdapter.episodeList = it
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        val decorator = DividerItemDecoration(context, GridLayoutManager.VERTICAL)
        decorator.setDrawable(resources.getDrawable(R.drawable.separator, null))
        personageDetailRecyclerView.addItemDecoration(decorator)
    }

    private fun getMyId(url: String): Int =
        (url.substring(url.lastIndexOf("/") + 1)).toInt()

    interface GoLocation {
        fun onGoLocation(myId: Int)
    }
}