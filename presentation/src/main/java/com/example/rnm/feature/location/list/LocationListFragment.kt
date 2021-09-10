package com.example.rnm.feature.location.list

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
import com.example.domain.entity.Location
import com.example.domain.entity.SearchLocation
import com.example.domain.interactors.IListInteractor
import com.example.rnm.R
import com.example.rnm.databinding.FragmentLocationListBinding
import com.example.rnm.di.Injector
import com.example.rnm.feature.base.BaseFragment
import com.example.rnm.feature.interf.OnLoadNextPage
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LocationListFragment : BaseFragment<LocationListViewModel>(),
    LocationFilterFragment.DialogCallback {

    private var startPage = 1
    private var filterPage = 1
    private var isFilter = false
    private lateinit var binding: FragmentLocationListBinding
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var itemLocationSelected: ItemLocationSelected
    private lateinit var locationFilterFragment: LocationFilterFragment

    companion object {
        fun newInstance() = LocationListFragment()
        const val LOCATION_LIST_TAG = "LOCATION_LIST_TAG"
        var filterLocation = SearchLocation()
        var allSizePage = 1
        var filterSizePage = 1
    }

    @Inject
    lateinit var locationInteractor: IListInteractor

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemLocationSelected = context as ItemLocationSelected
        locationAdapter = LocationAdapter({ i ->
            itemLocationSelected.onItemLocationSelected(i)
        }, object : OnLoadNextPage {
            override fun onLoad() {
                if (!isFilter) {
                    if (startPage != allSizePage){
                        startPage++
                        viewModel.getLocation(startPage)
                    }

                } else {
                    while (filterPage != filterSizePage){
                        filterPage++
                        viewModel.getLocation(filterPage, filterLocation)
                    }

                }
            }
        }
        )
        Injector.locationListFragmentComponent.inject(this)
        injectViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            locationAdapter.locationList
            viewModel.getLocation(startPage)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        locationRecyclerView.adapter = locationAdapter
        viewModel.locationLiveData.observe(viewLifecycleOwner) {
            locationAdapter.locationList = it
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
        locationRecyclerView.addItemDecoration(decorator)

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
            locationFilterFragment = LocationFilterFragment.newInstance()
            locationFilterFragment.show(childFragmentManager, "Tag")
            isFilter = true
        }
    }

    fun nameSearch(text: String) {
        viewModel.locationLiveData.observe(viewLifecycleOwner) {
            val filterList = ArrayList<Location>()
            for (i in it) {
                if (i.locationName.lowercase(Locale.getDefault())
                        .contains(text.lowercase(Locale.getDefault()))
                ) filterList.add(i)
            }
            locationAdapter.setDate(filterList)
        }
    }

    override fun onFilter() {
        if (filterLocation.type != null) {
            filterPage = 1
            viewModel.updateList()
            viewModel.getLocation(filterPage, filterLocation)
            viewModel.locationLiveData.observe(viewLifecycleOwner) {
                locationAdapter.setDate(it)
            }
        } else {
                    isFilter = false
                    startPage = 1
                    viewModel.getLocation(startPage)
                    viewModel.updateList()
        }
    }

    interface ItemLocationSelected {
        fun onItemLocationSelected(id: Int)
    }
}