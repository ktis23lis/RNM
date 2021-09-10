package com.example.rnm.feature.location.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.interactors.IDetailInteractor
import com.example.rnm.R
import com.example.rnm.databinding.FragmentLocationDetailsBinding
import com.example.rnm.di.Injector
import com.example.rnm.feature.base.BaseFragment
import com.example.rnm.feature.interf.GoBackFragment
import com.example.rnm.feature.interf.OnLoadNextPage
import com.example.rnm.feature.location.list.LocationListFragment
import com.example.rnm.feature.personage.list.PersonageAdapter
import com.example.rnm.feature.personage.list.PersonageListFragment
import javax.inject.Inject

class LocationDetailFragment: BaseFragment<LocationDetailViewModel>(){

    private var myId = 0
    private lateinit var goPersonageBack: GoBackFragment
    private lateinit var personageAdapter: PersonageAdapter
    private lateinit var binding : FragmentLocationDetailsBinding
    private lateinit var itemPersonageSelected: PersonageListFragment.ItemPersonageSelected

    companion object{
        fun newInstance(id: Int) = LocationDetailFragment().apply {
            val bundle = Bundle()
            bundle.putInt(LocationListFragment.LOCATION_LIST_TAG, id)
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
        itemPersonageSelected = context as PersonageListFragment.ItemPersonageSelected
        Injector.locationDetailFragmentComponent.inject(this)
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
            myId = requireArguments().getInt(LocationListFragment.LOCATION_LIST_TAG)
            viewModel.getLocation(myId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        locationDetailRecyclerView.adapter = personageAdapter
        viewModel.locationLiveData.observe(viewLifecycleOwner) {
            locationName.text = it.locationName
            locationType.text = it.locationType
            locationDimension.text = it.locationDimension
            viewModel.personageLiveData.observe(viewLifecycleOwner) {
                personageAdapter.personageList = it
            }
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        toolbar.setNavigationOnClickListener {
            goPersonageBack.onGoingBack()
        }
        val decorator = DividerItemDecoration(context, GridLayoutManager.VERTICAL)
        decorator.setDrawable(resources.getDrawable(R.drawable.separator, null))
        locationDetailRecyclerView.addItemDecoration(decorator)
    }
}