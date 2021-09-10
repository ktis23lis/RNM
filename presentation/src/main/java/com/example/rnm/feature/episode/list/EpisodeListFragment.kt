package com.example.rnm.feature.episode.list

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.entity.Episode
import com.example.domain.entity.SearchEpisode
import com.example.domain.interactors.IListInteractor
import com.example.rnm.R
import com.example.rnm.databinding.FragmentEpisodeListBinding
import com.example.rnm.di.Injector
import com.example.rnm.feature.base.BaseFragment
import com.example.rnm.feature.interf.OnLoadNextPage
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class EpisodeListFragment : BaseFragment<EpisodeListViewModel>(),
    EpisodeFilterFragment.DialogCallback {

    private var startPage = 1
    private var filterPage = 1
    private var isFilter = false
    private lateinit var itemEpisodeSelected: ItemEpisodeSelected
    private lateinit var binding: FragmentEpisodeListBinding
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var episodeFilterFragment: EpisodeFilterFragment

    companion object {
        fun newInstance() = EpisodeListFragment()
        const val EPISODE_LIST_TAG = "EPISODE_LIST_TAG"
        var filterEpisode = SearchEpisode()
        var allSizePage = 1
        var filterSizePage = 1
    }

    @Inject
    lateinit var episodeInteractor: IListInteractor

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemEpisodeSelected = context as ItemEpisodeSelected
        episodeAdapter = EpisodeAdapter({ i ->
            itemEpisodeSelected.onItemEpisodeSelected(i)
        }, object : OnLoadNextPage {
            override fun onLoad() {
                if (!isFilter) {
                    if (startPage != allSizePage){
                        startPage++
                        viewModel.getEpisode(startPage)
                    }
                } else {
                    while (filterPage != filterSizePage){
                        filterPage++
                        viewModel.getEpisode(filterPage, filterEpisode)
                    }
                }
            }
        }
        )
        Injector.episodeListFragmentComponent.inject(this)
        injectViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            episodeAdapter.episodeList
            viewModel.getEpisode(startPage)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        episodeRecyclerView.adapter = episodeAdapter
        viewModel.episodeLiveData.observe(viewLifecycleOwner) {
            episodeAdapter.episodeList = it
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(R.string.internet_error), Toast.LENGTH_LONG)
                .show()
        }

        val decorator = DividerItemDecoration(context, GridLayoutManager.VERTICAL)
        decorator.setDrawable(resources.getDrawable(R.drawable.separator, null))
        episodeRecyclerView.addItemDecoration(decorator)

        searchEdinText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                nameSearch(s.toString())
            }
        })

        buttonFilter.setOnClickListener {
            episodeFilterFragment = EpisodeFilterFragment.newInstance()
            episodeFilterFragment.show(childFragmentManager, "Tag")
            isFilter = true
        }

    }

    fun nameSearch(text: String) {
        viewModel.episodeLiveData.observe(viewLifecycleOwner) {
            val filterList = ArrayList<Episode>()
            for (i in it) {
                if (i.episodeName.lowercase(Locale.getDefault())
                        .contains(text.lowercase(Locale.getDefault()))
                ) filterList.add(i)
            }
            episodeAdapter.setDate(filterList)
        }
    }

    override fun onFilter() {
        if (filterEpisode.season != null) {
            filterPage = 1
            viewModel.updateList()
            viewModel.getEpisode(filterPage, filterEpisode)
            viewModel.episodeLiveData.observe(viewLifecycleOwner) {
                episodeAdapter.setDate(it)
            }
        } else {
            when (episodeAdapter.itemCount) {
                1 -> episodeFilterFragment.dismiss()
                else -> {
                   isFilter = false
                    startPage = 1
                    viewModel.getEpisode(startPage)
                    viewModel.updateList()
                }
            }
        }
        goHomePersonage(filterEpisode)
    }

    private fun goHomePersonage(filterEpisode: SearchEpisode) {
        val equalsEpisode = SearchEpisode(null)
        if (equalsEpisode.equals(filterEpisode)) {
            startPage = 1
            viewModel.updateList()
            viewModel.getEpisode(startPage)
            viewModel.episodeLiveData.observe(viewLifecycleOwner) {
                episodeAdapter.episodeList = it
            }
        }
    }

    interface ItemEpisodeSelected {
        fun onItemEpisodeSelected(id: Int)
    }
}